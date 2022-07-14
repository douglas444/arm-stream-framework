package br.ufu.facom.armstream.api.data;

import java.util.List;

public interface ArmInterceptionResult {

    ArmClusterCategory getPrediction();
    List<ArmDataInstance> getLabeledDataInstances();

}
