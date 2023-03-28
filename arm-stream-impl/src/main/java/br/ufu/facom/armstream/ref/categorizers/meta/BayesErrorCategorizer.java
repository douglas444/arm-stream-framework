package br.ufu.facom.armstream.ref.categorizers.meta;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.modules.ArmMetaCategorizer;

public abstract class BayesErrorCategorizer implements ArmMetaCategorizer, Cloneable {

    protected double thresholdFactor;
    protected int dimensionality;

    public BayesErrorCategorizer(final double thresholdFactor, final int dimensionality) {
        this.thresholdFactor = thresholdFactor;
        this.dimensionality = dimensionality;
    }

    @Override
    public ArmClusterCategory categorize(final ArmInterceptionContext context) {

        final long numberOfClasses = context.getDataClassesSummary()
                .stream()
                .map(ArmClusterSummary::getLabel)
                .distinct()
                .count();

        final double bayesErrorEstimation = calculateBayesErrorEstimation(context);

        final double minClassificationProbability = 1 / (double) numberOfClasses;
        final double maxClassificationError = 1 - minClassificationProbability;
        final double threshold = maxClassificationError * this.thresholdFactor;

        if (bayesErrorEstimation > threshold) {
            return ArmClusterCategory.NOVELTY;
        } else {
            return ArmClusterCategory.KNOWN;
        }
    }

    @Override
    public BayesErrorCategorizer clone() {
        try {
            return (BayesErrorCategorizer) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract double calculateBayesErrorEstimation(ArmInterceptionContext context);

    public BayesErrorCategorizer withThresholdFactor(final double thresholdFactor) {
        final BayesErrorCategorizer clone = this.clone();
        clone.thresholdFactor = thresholdFactor;
        return clone;
    }

    public BayesErrorCategorizer withDimensionality(final int dimensionality) {
        final BayesErrorCategorizer clone = this.clone();
        clone.dimensionality = dimensionality;
        return clone;
    }

    //Getters

    public double getThresholdFactor() {
        return thresholdFactor;
    }

    public int getDimensionality() {
        return dimensionality;
    }
}
