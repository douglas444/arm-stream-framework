package br.ufu.facom.armstream.ref.util.algorithms;

import br.ufu.facom.armstream.ref.util.datastructures.Cluster;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.*;

public final class KMeans {

    public static List<Cluster> execute(final List<Sample> samples, final int k, final Random random) {
        return execute(samples, chooseCentroids(samples, k, random));
    }

    public static List<Cluster> execute(final List<Sample> samples, final List<Sample> centroids) {

        List<Cluster> clusters;
        Set<Sample> oldCentroids;

        do {

            clusters = new ArrayList<>();

            for (final Map.Entry<Sample, List<Sample>> entry : executeSingleIteration(samples, centroids).entrySet()) {
                final List<Sample> value = entry.getValue();
                if (!value.isEmpty()) {
                    clusters.add(new Cluster(value));
                }
            }

            oldCentroids = new HashSet<>(centroids);
            centroids.clear();

            clusters.stream()
                    .filter(cluster -> !cluster.isEmpty())
                    .map(Cluster::calculateCentroid)
                    .forEach(centroids::add);

        } while (!oldCentroids.containsAll(centroids));

        return clusters;
    }

    static List<Sample> chooseCentroids(final List<Sample> samples, final int k, final Random random) {

        final List<Sample> centroids = new ArrayList<>();
        final List<Sample> candidates = new ArrayList<>(samples);

        int n = k;

        while (n > 0 && !candidates.isEmpty()) {
            final int randomIndex = random.nextInt(candidates.size());
            final Sample centroid = candidates.get(randomIndex);
            candidates.remove(centroid);
            centroids.add(centroid);
            --n;
        }

        return centroids;

    }

    public static HashMap<Sample, List<Sample>> executeSingleIteration(final List<Sample> samples,
                                                                       final List<Sample> centroids) {

        final HashMap<Sample, List<Sample>> samplesByCentroid = new HashMap<>();

        centroids.forEach(centroid -> samplesByCentroid.put(centroid, new ArrayList<>()));

        samples.forEach(sample -> {
            final Sample closestCentroid = sample.calculateClosestSample(centroids);
            samplesByCentroid.get(closestCentroid).add(sample);
        });

        return samplesByCentroid;

    }
}
