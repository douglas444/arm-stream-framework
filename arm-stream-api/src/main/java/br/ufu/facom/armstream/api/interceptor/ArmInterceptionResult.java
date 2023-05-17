package br.ufu.facom.armstream.api.interceptor;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;

import java.util.List;

public interface ArmInterceptionResult {

    ArmClusterCategory getPrediction();

    List<ArmDataInstance> getLabeledDataInstances();

}
