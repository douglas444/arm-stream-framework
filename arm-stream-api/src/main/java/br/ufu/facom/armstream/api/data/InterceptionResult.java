package br.ufu.facom.armstream.api.data;

import java.util.List;

public interface InterceptionResult {

    ArmClusterCategory getPrediction();
    List<ArmDataInstance> getLabeledDataInstances();

}
