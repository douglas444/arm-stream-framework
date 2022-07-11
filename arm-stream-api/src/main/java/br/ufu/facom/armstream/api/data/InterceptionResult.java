package br.ufu.facom.armstream.api.data;

import java.util.ArrayList;
import java.util.List;

public class InterceptionResult {

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
