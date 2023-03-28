package br.ufu.facom.armstream.ref.util.algorithms;

import br.ufu.facom.armstream.ref.util.datastructures.Cluster;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.*;

public final class KMeansPlusPlus {

    public static List<Cluster> execute(final List<Sample> samples, final int k, final Random random) {
        return KMeans.execute(samples, chooseCentroids(samples, k, random));
    }

    private static List<Sample> chooseCentroids(final List<Sample> samples,
                                                final int k,
                                                final Random random) {

        final List<Sample> centroids = new ArrayList<>();
        for (int i = 0; i < k; ++i) {
            centroids.add(selectNextCentroid(samples, centroids, random));
        }
        return centroids;
    }

    private static Sample selectNextCentroid(final List<Sample> samples,
                                             final List<Sample> centroids,
                                             final Random random) {

        final HashMap<Sample, Double> probabilityBySample = mapProbabilityBySample(samples, centroids);
        final List<Map.Entry<Sample, Double>> entries = new ArrayList<>(probabilityBySample.entrySet());
        final Iterator<Map.Entry<Sample, Double>> iterator = entries.iterator();

        double cumulativeProbability = 0;
        Sample selected = null;

        final double r = random.nextDouble();

        while (selected == null) {

            final Map.Entry<Sample, Double> entry = iterator.next();
            cumulativeProbability += entry.getValue();

            if (r <= cumulativeProbability || !iterator.hasNext()) {
                selected = entry.getKey();
            }

        }

        return selected;
    }

    private static HashMap<Sample, Double> mapProbabilityBySample(final List<Sample> samples,
                                                                  final List<Sample> centroids) {

        final HashMap<Sample, Double> probabilityBySample = new HashMap<>();

        samples.forEach(sample -> {

            final double distance;
            if (centroids.isEmpty()) {
                distance = 0;
            } else {
                final Sample closestCentroid = sample.calculateClosestSample(centroids);
                distance = closestCentroid.distance(sample);
            }

            probabilityBySample.put(sample, Math.pow(distance, 2));
        });

        final double sum = probabilityBySample.values().stream().reduce(0.0, Double::sum);
        probabilityBySample.replaceAll((sample, probability) -> probability / (sum == 0 ? 1 : sum));

        return probabilityBySample;

    }

}
