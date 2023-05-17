package br.ufu.facom.armstream.http;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class ArmInterceptionContextDTO implements ArmInterceptionContext {

    @JsonProperty("cluster_summary")
    private ArmClusterSummaryDTO clusterSummary;
    @JsonProperty("cluster_data_instances")
    private List<ArmDataInstanceDTO> clusterDataInstances;
    @JsonProperty("predicted_category")
    private ArmClusterCategory predictedCategory;
    @JsonProperty("data_classes_summary")
    private List<ArmClusterSummaryDTO> dataClassesSummary;

    public ArmInterceptionContextDTO() {
    }

    public ArmInterceptionContextDTO(ArmClusterSummaryDTO clusterSummary,
                                     List<ArmDataInstanceDTO> clusterDataInstances,
                                     ArmClusterCategory predictedCategory,
                                     List<ArmClusterSummaryDTO> dataClassesSummary) {
        this.clusterSummary = clusterSummary;
        this.clusterDataInstances = clusterDataInstances;
        this.predictedCategory = predictedCategory;
        this.dataClassesSummary = dataClassesSummary;
    }

    @Override
    public ArmClusterSummaryDTO getClusterSummary() {
        return clusterSummary;
    }

    public void setClusterSummary(ArmClusterSummaryDTO clusterSummary) {
        this.clusterSummary = clusterSummary;
    }

    @Override
    public List<ArmDataInstance> getClusterDataInstances() {
        return clusterDataInstances.stream()
                .map(armDataInstance -> (ArmDataInstance) armDataInstance)
                .collect(Collectors.toList());
    }

    public void setClusterDataInstances(List<ArmDataInstanceDTO> clusterDataInstances) {
        this.clusterDataInstances = clusterDataInstances;
    }

    @Override
    public ArmClusterCategory getPredictedCategory() {
        return predictedCategory;
    }

    public void setPredictedCategory(ArmClusterCategory predictedCategory) {
        this.predictedCategory = predictedCategory;
    }

    @Override
    public List<ArmClusterSummary> getDataClassesSummary() {
        return dataClassesSummary.stream()
                .map(armClusterSummary -> (ArmClusterSummary) armClusterSummary)
                .collect(Collectors.toList());
    }

    public void setDataClassesSummary(List<ArmClusterSummaryDTO> dataClassesSummary) {
        this.dataClassesSummary = dataClassesSummary;
    }
}
