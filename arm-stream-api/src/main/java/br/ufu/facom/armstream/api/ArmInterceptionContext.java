package br.ufu.facom.armstream.api;

import java.util.List;

public interface ArmInterceptionContext {

    List<ArmDataInstance> getClusterInstances();
    ArmClusterCategory getClusterCategory();
    List<ArmClusterSummary> getDataClassesSummary();

}
