package br.ufu.facom.armstream.ref.echo;

import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.*;

public final class MCIKMeans {

    public static List<ImpurityBasedCluster> execute(final List<Sample> labeledSamples,
                                                     final List<Sample> unlabeledSamples,
                                                     final int maxIterations,
                                                     final int conditionalModeMaxIterations,
                                                     final double centroidsPercentage,
                                                     final Random random) {

        if (labeledSamples.size() == 0) {
            throw new IllegalArgumentException();
        }

        final HashMap<Integer, List<Sample>> samplesByLabel = new HashMap<>();

        labeledSamples.forEach(labeledSample -> {
            samplesByLabel.putIfAbsent(labeledSample.getY(), new ArrayList<>());
            samplesByLabel.get(labeledSample.getY()).add(labeledSample);
        });

        final int k = (int) ((labeledSamples.size() + unlabeledSamples.size()) / (double) 100 * centroidsPercentage);
        final List<List<Sample>> lists = new ArrayList<>(samplesByLabel.values());
        final int[] numbersOfCentroids = new int[samplesByLabel.values().size()];
        double remaining = 0;

        for (int i = 0; i < lists.size(); ++i) {
            final List<Sample> samples = lists.get(i);
            final double rate = k * (double) samples.size() / labeledSamples.size();
            remaining += rate - (int) rate;
            numbersOfCentroids[i] = (int) rate;
        }

        while (remaining > 0) {

            --remaining;

            int minRate = -1;
            for (int i = 0; i < numbersOfCentroids.length; ++i) {
                if (minRate == -1 || numbersOfCentroids[i] < numbersOfCentroids[minRate]) {
                    minRate = i;
                }
            }

            ++numbersOfCentroids[minRate];

        }

        final List<Sample> centroids = new ArrayList<>();
        for (int i = 0; i < lists.size(); ++i) {

            final List<Sample> samples = lists.get(i);
            centroids.addAll(chooseCentroids(samples, numbersOfCentroids[i], random));

            if (centroids.size() < numbersOfCentroids[i] && !unlabeledSamples.isEmpty()) {
                final List<Sample> fillingSamples = new ArrayList<>(unlabeledSamples);
                Collections.shuffle(fillingSamples, random);
                while (centroids.size() < numbersOfCentroids[i] && !fillingSamples.isEmpty()) {
                    centroids.add(fillingSamples.remove(0));
                }

            }

        }

        return execute(
                labeledSamples,
                unlabeledSamples,
                maxIterations,
                conditionalModeMaxIterations,
                centroids,
                random);

    }

    private static List<ImpurityBasedCluster> execute(final List<Sample> labeledSamples,
                                                      final List<Sample> unlabeledSamples,
                                                      final int maxIterations,
                                                      final int conditionalModeMaxIterations,
                                                      final List<Sample> centroids,
                                                      final Random random) {

        final List<ImpurityBasedCluster> clusters = new ArrayList<>();
        final HashMap<Integer, ImpurityBasedCluster> clusterById = new HashMap<>();

        for (int i = 0; i < centroids.size(); ++i) {
            clusters.add(new ImpurityBasedCluster(i, centroids.get(i)));
            clusterById.put(i, clusters.get(i));
        }

        boolean changing;
        int iterations = maxIterations;
        do {

            changing = iterativeConditionalMode(
                    labeledSamples,
                    unlabeledSamples,
                    clusters,
                    clusterById,
                    random,
                    conditionalModeMaxIterations);

            clusters.stream().filter(cluster -> cluster.size() > 0).forEach(ImpurityBasedCluster::updateCentroid);
            --iterations;

        } while (changing && iterations > 0);

        clusters.removeIf(cluster -> cluster.size() < 2);

        labeledSamples.forEach(sample -> sample.setId(null));
        unlabeledSamples.forEach(sample -> sample.setId(null));

        return clusters;

    }

    private static List<Sample> chooseCentroids(final List<Sample> samples, final int k, final Random random) {

        if (samples.size() <= k) {
            return samples;
        }

        final List<Sample> centroids = new ArrayList<>();
        final List<Sample> candidates = new ArrayList<>(samples);

        for (int i = 0; i < k; ++i) {
            final Sample selected = candidates.get(random.nextInt(candidates.size()));
            candidates.remove(selected);
            centroids.add(selected);
        }

        return centroids;

    }

    private static boolean iterativeConditionalMode(List<Sample> labeledSamples,
                                                    List<Sample> unlabeledSamples,
                                                    final List<ImpurityBasedCluster> clusters,
                                                    final HashMap<Integer, ImpurityBasedCluster> clusterById,
                                                    final Random random,
                                                    final int maxIterations) {

        assert !labeledSamples.isEmpty();

        labeledSamples = new ArrayList<>(labeledSamples);
        unlabeledSamples = new ArrayList<>(unlabeledSamples);

        boolean changed;
        boolean noChanges = true;
        int iterations = maxIterations;
        do {

            Collections.shuffle(labeledSamples, random);
            Collections.shuffle(unlabeledSamples, random);

            final int numberOfSamples = labeledSamples.size() + unlabeledSamples.size();

            changed = false;

            for (int i = 0; i < numberOfSamples; ++i) {

                final boolean isLabeled;
                final Sample sample;

                if (unlabeledSamples.isEmpty() || random.nextBoolean()) {
                    isLabeled = true;
                    sample = labeledSamples.remove(0);
                } else {
                    isLabeled = false;
                    sample = unlabeledSamples.remove(0);
                }

                final Comparator<ImpurityBasedCluster> comparator = Comparator.comparing((cluster) -> {

                    if (isLabeled) {
                        return sample.distance(cluster.getCentroid()) *
                                (1 + cluster.getEntropy() * cluster.dissimilarityCount(sample));
                    } else {
                        return sample.distance(cluster.getCentroid());
                    }

                });

                final Integer oldId = sample.getId();

                if (sample.getId() != null) {
                    if (isLabeled) {
                        clusterById.get(sample.getId()).removeLabeledSample(sample);
                    } else {
                        clusterById.get(sample.getId()).removeUnlabeledSample(sample);
                    }
                    sample.setId(null);
                }

                ImpurityBasedCluster chosenCluster = clusters.get(0);

                for (int j = 1; j < clusters.size(); j++) {
                    final ImpurityBasedCluster cluster = clusters.get(j);
                    if (comparator.compare(cluster, chosenCluster) < 0) {
                        chosenCluster = cluster;
                    }
                }

                if (isLabeled) {
                    chosenCluster.addLabeledSample(sample);
                } else {
                    chosenCluster.addUnlabeledSample(sample);
                }

                sample.setId(chosenCluster.getId());
                chosenCluster.updateEntropy();

                if (!chosenCluster.getId().equals(oldId)) {
                    changed = true;
                    noChanges = false;
                }

            }
            --iterations;

        } while (changed && iterations > 0);

        return !noChanges;

    }

}
