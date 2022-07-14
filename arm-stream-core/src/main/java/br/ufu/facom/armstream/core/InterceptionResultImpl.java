package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.data.ArmClusterCategory;
import br.ufu.facom.armstream.api.data.ArmDataInstance;
import br.ufu.facom.armstream.api.data.InterceptionResult;

import java.util.ArrayList;
import java.util.List;

public class InterceptionResultImpl implements InterceptionResult {

    private final ArmClusterCategory clusterCategory;
    private final List<ArmDataInstance> labeledDataInstances;

    public InterceptionResultImpl(ArmClusterCategory clusterCategory, List<ArmDataInstance> labeledDataInstances) {
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
