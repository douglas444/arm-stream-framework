package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.data.ArmClusterCategory;

public class InterceptionLog {

    private final ArmClusterCategory trueCategory;
    private final ArmClusterCategory basePrediction;
    private final ArmClusterCategory metaPrediction;
    private final ArmClusterCategory activePrediction;

    public InterceptionLog(ArmClusterCategory trueCategory,
                           ArmClusterCategory predictedByBaseCategorizer,
                           ArmClusterCategory predictedByMetaCategorizer,
                           ArmClusterCategory predictedByActiveCategorizer) {

        this.trueCategory = trueCategory;
        this.basePrediction = predictedByBaseCategorizer;
        this.metaPrediction = predictedByMetaCategorizer;
        this.activePrediction = predictedByActiveCategorizer;
    }

    public ArmClusterCategory getTrueCategory() {
        return trueCategory;
    }

    public ArmClusterCategory getBasePrediction() {
        return basePrediction;
    }

    public ArmClusterCategory getMetaPrediction() {
        return metaPrediction;
    }

    public ArmClusterCategory getActivePrediction() {
        return activePrediction;
    }

}
