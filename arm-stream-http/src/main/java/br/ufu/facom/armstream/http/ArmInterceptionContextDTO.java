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

    public ArmInterceptionContextDTO(final ArmClusterSummaryDTO clusterSummary,
                                     final List<ArmDataInstanceDTO> clusterDataInstances,
                                     final ArmClusterCategory predictedCategory,
                                     final List<ArmClusterSummaryDTO> dataClassesSummary) {
        this.clusterSummary = clusterSummary;
        this.clusterDataInstances = clusterDataInstances;
        this.predictedCategory = predictedCategory;
        this.dataClassesSummary = dataClassesSummary;
    }

    @Override
    public ArmClusterSummaryDTO getClusterSummary() {
        return clusterSummary;
    }

    public void setClusterSummary(final ArmClusterSummaryDTO clusterSummary) {
        this.clusterSummary = clusterSummary;
    }

    @Override
    public List<ArmDataInstance> getClusterDataInstances() {
        return clusterDataInstances.stream()
                .map(armDataInstance -> (ArmDataInstance) armDataInstance)
                .collect(Collectors.toList());
    }

    public void setClusterDataInstances(final List<ArmDataInstanceDTO> clusterDataInstances) {
        this.clusterDataInstances = clusterDataInstances;
    }

    @Override
    public ArmClusterCategory getPredictedCategory() {
        return predictedCategory;
    }

    public void setPredictedCategory(final ArmClusterCategory predictedCategory) {
        this.predictedCategory = predictedCategory;
    }

    @Override
    public List<ArmClusterSummary> getDataClassesSummary() {
        return dataClassesSummary.stream()
                .map(armClusterSummary -> (ArmClusterSummary) armClusterSummary)
                .collect(Collectors.toList());
    }

    public void setDataClassesSummary(final List<ArmClusterSummaryDTO> dataClassesSummary) {
        this.dataClassesSummary = dataClassesSummary;
    }
}
