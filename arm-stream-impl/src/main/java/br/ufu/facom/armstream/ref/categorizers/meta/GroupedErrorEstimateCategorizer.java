package br.ufu.facom.armstream.ref.categorizers.meta;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.modules.ArmMetaCategorizer;

public abstract class GroupedErrorEstimateCategorizer implements ArmMetaCategorizer, Cloneable {

    protected double thresholdFactor;
    protected int dimensionality;

    public GroupedErrorEstimateCategorizer(final double thresholdFactor, final int dimensionality) {
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

        if (numberOfClasses == 0) {
            return ArmClusterCategory.NOVELTY;
        }

        if (numberOfClasses == 1) {
            return context.getPredictedCategory();
        }

        final double groupedErrorEstimate = calculateBayesErrorEstimation(context);

        final double minClassificationProbability = 1 / (double) numberOfClasses;
        final double maxClassificationError = 1 - minClassificationProbability;
        final double threshold = maxClassificationError * this.thresholdFactor;

        if (groupedErrorEstimate > threshold) {
            return ArmClusterCategory.NOVELTY;
        } else {
            return ArmClusterCategory.KNOWN;
        }
    }

    @Override
    public GroupedErrorEstimateCategorizer clone() {
        try {
            return (GroupedErrorEstimateCategorizer) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract double calculateBayesErrorEstimation(ArmInterceptionContext context);

    public GroupedErrorEstimateCategorizer withThresholdFactor(final double thresholdFactor) {
        final GroupedErrorEstimateCategorizer clone = this.clone();
        clone.thresholdFactor = thresholdFactor;
        return clone;
    }

    public GroupedErrorEstimateCategorizer withDimensionality(final int dimensionality) {
        final GroupedErrorEstimateCategorizer clone = this.clone();
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
