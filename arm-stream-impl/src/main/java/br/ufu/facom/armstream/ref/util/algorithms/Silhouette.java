package br.ufu.facom.armstream.ref.util.algorithms;

import br.ufu.facom.armstream.ref.util.datastructures.Cluster;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;
import br.ufu.facom.armstream.ref.util.datastructures.SampleDistanceComparator;

import java.util.List;
import java.util.stream.Collectors;

public class Silhouette {

    public static double calculate(final Cluster cluster, final List<Sample> modelCentroids) {

        final Sample centroid = cluster.calculateCentroid();
        final double a = cluster.calculateStandardDeviation();
        final double b;

        if (modelCentroids.size() > 0) {

            final List<Sample> sortedModelCentroids = modelCentroids
                    .stream()
                    .sorted(new SampleDistanceComparator(centroid))
                    .collect(Collectors.toList());

            b = centroid.distance(sortedModelCentroids.get(0));

        } else {
            b = Double.MAX_VALUE;
        }

        return (b - a) / Math.max(b, a);

    }

}
