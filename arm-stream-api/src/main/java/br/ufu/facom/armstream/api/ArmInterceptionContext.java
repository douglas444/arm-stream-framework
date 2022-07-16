package br.ufu.facom.armstream.api;

import java.util.List;

public interface ArmInterceptionContext {

    List<ArmDataInstance> getClusterDataInstances();
    ArmClusterCategory getPredictedCategory();
    List<ArmClusterSummary> getDataClassesSummary();

}
