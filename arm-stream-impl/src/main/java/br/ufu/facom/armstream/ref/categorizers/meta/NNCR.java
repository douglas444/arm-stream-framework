package br.ufu.facom.armstream.ref.categorizers.meta;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;

public class NNCR extends GroupedErrorEstimateCategorizer {

    public NNCR() {
        super(0.8, 1);
    }

    public NNCR(final double thresholdFactor, final int dimensionality) {
        super(thresholdFactor, dimensionality);
    }

    @Override
    protected double calculateBayesErrorEstimation(final ArmInterceptionContext context) {

        final NextNeighbourClassifier classifier = new NextNeighbourClassifier(
                context.getDataClassesSummary(),
                this.dimensionality,
                NextNeighbourClassifier.EUCLIDEAN_DISTANCE);

        return 1 - classifier.classificationProbability(context.getClusterSummary().getCentroidAttributes());
    }

}
