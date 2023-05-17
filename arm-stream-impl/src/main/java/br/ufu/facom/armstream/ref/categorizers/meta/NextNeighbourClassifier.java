package br.ufu.facom.armstream.ref.categorizers.meta;

import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class NextNeighbourClassifier {

    public static final BiFunction<ArmClusterSummary, double[], Double> EUCLIDEAN_DISTANCE =
            (cluster, x) -> Sample.distance(cluster.getCentroidAttributes(), x);

    public static final BiFunction<ArmClusterSummary, double[], Double> EUCLIDEAN_DISTANCE_WEIGHTED_BY_STDEV =
            (cluster, x) -> Sample.distance(cluster.getCentroidAttributes(), x) * cluster.getStandardDeviation();

    private final List<ArmClusterSummary> dataClassesSummary;
    private final int dimensionality;
    private final Set<Integer> knownLabels;
    private final BiFunction<ArmClusterSummary, double[], Double> similarityFunction;

    public NextNeighbourClassifier(final List<ArmClusterSummary> dataClassesSummary,
                                   final int dimensionality,
                                   final BiFunction<ArmClusterSummary, double[], Double> similarityFunction) {

        this.dataClassesSummary = dataClassesSummary;
        this.dimensionality = dimensionality;
        this.similarityFunction = similarityFunction;
        this.knownLabels = dataClassesSummary
                .stream()
                .map(ArmClusterSummary::getLabel)
                .collect(Collectors.toSet());

    }

    public double classificationProbability(final double[] x) {

        final List<ArmClusterSummary> closestClusters = new ArrayList<>();

        this.knownLabels.forEach((knownLabel) -> {

            this.dataClassesSummary
                    .stream()
                    .filter(clusterSummary -> clusterSummary.getLabel().equals(knownLabel))
                    .min(Comparator.comparing(cluster -> this.similarityFunction.apply(cluster, x)))
                    .ifPresent(closestClusters::add);

        });

        final Double distanceToClosest = closestClusters
                .stream()
                .map(cluster -> this.similarityFunction.apply(cluster, x))
                .min(Double::compare)
                .orElseThrow(() -> new IllegalStateException("Cannot calculate the classification probability if " +
                        "there's no known classes"));

        if (distanceToClosest == 0) { // The distance to the closest is 0
            return 1; // In this case we assume a classification probability of 1 so there's no division by zero below
        }

        final double inverseDistanceToClosest = Math.pow(1.0 / distanceToClosest, this.dimensionality);

        final double inverseDistanceSum = closestClusters
                .stream()
                .map(cluster -> this.similarityFunction.apply(cluster, x))
                .map(value -> Math.pow(1.0 / value, this.dimensionality))
                .reduce(0.0, Double::sum);

        return inverseDistanceToClosest / inverseDistanceSum;

    }

}
