package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.ArmClusterCategory;

class InterceptionLog {

    private final ArmClusterCategory trueCategory;
    private final ArmClusterCategory basePrediction;
    private final ArmClusterCategory metaPrediction;
    private final ArmClusterCategory activePrediction;

    InterceptionLog(ArmClusterCategory trueCategory,
                           ArmClusterCategory predictedByBaseCategorizer,
                           ArmClusterCategory predictedByMetaCategorizer,
                           ArmClusterCategory predictedByActiveCategorizer) {

        this.trueCategory = trueCategory;
        this.basePrediction = predictedByBaseCategorizer;
        this.metaPrediction = predictedByMetaCategorizer;
        this.activePrediction = predictedByActiveCategorizer;
    }

    ArmClusterCategory getTrueCategory() {
        return trueCategory;
    }

    ArmClusterCategory getBasePrediction() {
        return basePrediction;
    }

    ArmClusterCategory getMetaPrediction() {
        return metaPrediction;
    }

    ArmClusterCategory getActivePrediction() {
        return activePrediction;
    }

}
