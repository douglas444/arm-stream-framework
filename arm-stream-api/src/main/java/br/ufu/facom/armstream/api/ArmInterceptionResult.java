package br.ufu.facom.armstream.api;

import java.util.List;

public interface ArmInterceptionResult {

    ArmClusterCategory getCategory();
    List<ArmDataInstance> getLabeledDataInstances();

}
