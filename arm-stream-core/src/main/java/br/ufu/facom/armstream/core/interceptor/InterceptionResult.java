package br.ufu.facom.armstream.core.interceptor;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;

import java.util.Collections;
import java.util.List;

public class InterceptionResult implements ArmInterceptionResult {

    private final ArmClusterCategory prediction;

    public InterceptionResult(final ArmClusterCategory prediction) {
        this.prediction = prediction;
    }

    public ArmClusterCategory getPrediction() {
        return prediction;
    }

    public List<ArmDataInstance> getLabeledDataInstances() {
        return Collections.emptyList();
    }

}