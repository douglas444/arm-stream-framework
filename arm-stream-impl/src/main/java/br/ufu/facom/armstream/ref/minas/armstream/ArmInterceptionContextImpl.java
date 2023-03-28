package br.ufu.facom.armstream.ref.minas.armstream;

import br.ufu.facom.armstream.ref.minas.MicroCluster;
import br.ufu.facom.armstream.ref.minas.MicroClusterCategory;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;
import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.ufu.facom.armstream.api.datastructure.ArmClusterCategory.KNOWN;
import static br.ufu.facom.armstream.api.datastructure.ArmClusterCategory.NOVELTY;

public class ArmInterceptionContextImpl implements ArmInterceptionContext {

    private final ArmClusterSummary clusterSummary;
    private final List<ArmDataInstance> clusterDataInstances;
    private ArmClusterCategory predictedCategory;
    private final List<ArmClusterSummary> dataClassesSummary;


    public ArmInterceptionContextImpl(final MicroCluster targetMicroCluster,
                                      final List<Sample> samples,
                                      final List<MicroCluster> decisionModelMicroClusters,
                                      final List<MicroCluster> sleepMemoryMicroClusters) {

        this.clusterSummary = new ArmClusterSummaryImpl(targetMicroCluster);
        this.clusterDataInstances = samples.stream().map(ArmDataInstanceImpl::new).collect(Collectors.toList());
        this.dataClassesSummary = new ArrayList<>();

        decisionModelMicroClusters
                .stream()
                .filter(microCluster -> microCluster.getMicroClusterCategory() == MicroClusterCategory.KNOWN)
                .map(ArmClusterSummaryImpl::new)
                .forEach(this.dataClassesSummary::add);

        sleepMemoryMicroClusters
                .stream()
                .filter(microCluster -> microCluster.getMicroClusterCategory() == MicroClusterCategory.KNOWN)
                .map(ArmClusterSummaryImpl::new)
                .forEach(this.dataClassesSummary::add);

    }


    @Override
    public ArmClusterSummary getClusterSummary() {
        return this.clusterSummary;
    }

    @Override
    public List<ArmDataInstance> getClusterDataInstances() {
        return this.clusterDataInstances;
    }

    @Override
    public ArmClusterCategory getPredictedCategory() {
        return this.predictedCategory;
    }

    @Override
    public List<ArmClusterSummary> getDataClassesSummary() {
        return this.dataClassesSummary;
    }

    public void setPredictedCategory(final MicroClusterCategory predictedCategory) {
        this.predictedCategory = predictedCategory == MicroClusterCategory.KNOWN ? KNOWN : NOVELTY;
    }

}
