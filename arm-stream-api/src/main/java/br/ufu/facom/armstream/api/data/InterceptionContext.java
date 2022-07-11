package br.ufu.facom.armstream.api.data;

import java.util.List;

public interface InterceptionContext {

    List<ArmDataInstance> getClusterDataInstances();
    ArmClusterCategory getPredictedCategory();
    List<ArmClusterSummary> getDataClassesSummary();

}
