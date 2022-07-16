package br.ufu.facom.armstream.api;

import java.util.List;

public interface ArmInterceptionResult {

    ArmClusterCategory getPrediction();
    List<ArmDataInstance> getLabeledDataInstances();

}
