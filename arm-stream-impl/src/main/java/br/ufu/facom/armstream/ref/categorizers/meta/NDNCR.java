package br.ufu.facom.armstream.ref.categorizers.meta;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;

public class NDNCR extends BayesErrorCategorizer {

    public NDNCR() {
        super(0.9, 1);
    }

    public NDNCR(final double thresholdFactor, final int dimensionality) {
        super(thresholdFactor, dimensionality);
    }

    @Override
    protected double calculateBayesErrorEstimation(final ArmInterceptionContext context) {

        final NextNeighbourClassifier classifier = new NextNeighbourClassifier(
                context.getDataClassesSummary(),
                this.dimensionality,
                NextNeighbourClassifier.EUCLIDEAN_DISTANCE_WEIGHTED_BY_STDEV);

        return  1 - classifier.classificationProbability(context.getClusterSummary().getCentroidAttributes());
    }

}
