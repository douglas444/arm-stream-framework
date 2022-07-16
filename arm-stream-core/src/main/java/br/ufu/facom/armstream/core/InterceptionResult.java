package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.ArmClusterCategory;
import br.ufu.facom.armstream.api.ArmDataInstance;
import br.ufu.facom.armstream.api.ArmInterceptionResult;

import java.util.ArrayList;
import java.util.List;

public class InterceptionResult implements ArmInterceptionResult {

    private final ArmClusterCategory clusterCategory;
    private final List<ArmDataInstance> labeledDataInstances;

    public InterceptionResult(ArmClusterCategory clusterCategory, List<ArmDataInstance> labeledDataInstances) {
        this.clusterCategory = clusterCategory;
        this.labeledDataInstances = labeledDataInstances;
    }

    public ArmClusterCategory getPrediction() {
        return clusterCategory;
    }

    public List<ArmDataInstance> getLabeledDataInstances() {
        return labeledDataInstances == null ? new ArrayList<>() : labeledDataInstances;
    }

}
