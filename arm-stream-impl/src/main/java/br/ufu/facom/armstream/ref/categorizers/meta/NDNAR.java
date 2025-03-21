package br.ufu.facom.armstream.ref.categorizers.meta;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;

public class NDNAR extends GroupedErrorEstimateCategorizer {

    public NDNAR() {
        super(0.9, 1);
    }

    public NDNAR(final double thresholdFactor, final int dimensionality) {
        super(thresholdFactor, dimensionality);
    }

    @Override
    protected double calculateBayesErrorEstimation(final ArmInterceptionContext context) {

        if (context.getClusterDataInstances().isEmpty()) {
            throw new IllegalArgumentException();
        }

        final NextNeighbourClassifier classifier = new NextNeighbourClassifier(
                context.getDataClassesSummary(),
                super.dimensionality,
                NextNeighbourClassifier.EUCLIDEAN_DISTANCE_WEIGHTED_BY_STDEV);

        return context
                .getClusterDataInstances()
                .stream()
                .map(dataInstance -> 1 - classifier.classificationProbability(dataInstance.getAttributes()))
                .reduce(0.0, Double::sum) / (double) context.getClusterDataInstances().size();
    }

}