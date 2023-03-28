package br.ufu.facom.armstream.api.interceptor;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;

import java.util.List;

public interface ArmInterceptionContext {

    ArmClusterSummary getClusterSummary();
    List<ArmDataInstance> getClusterDataInstances();
    ArmClusterCategory getPredictedCategory();
    List<ArmClusterSummary> getDataClassesSummary();

}
