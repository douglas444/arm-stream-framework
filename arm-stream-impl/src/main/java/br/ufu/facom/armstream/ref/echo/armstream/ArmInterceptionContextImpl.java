package br.ufu.facom.armstream.ref.echo.armstream;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.ref.echo.ImpurityBasedCluster;
import br.ufu.facom.armstream.ref.echo.Model;
import br.ufu.facom.armstream.ref.util.datastructures.Cluster;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArmInterceptionContextImpl implements ArmInterceptionContext {

    private final ArmClusterSummary clusterSummary;
    private final List<ArmDataInstance> clusterDataInstances;
    private final ArmClusterCategory predictedCategory;
    private final List<ArmClusterSummary> dataClassesSummary;

    public ArmInterceptionContextImpl(final Cluster cluster,
                                      final ArmClusterCategory predictedCategory,
                                      final List<Model> ensemble) {

        this.predictedCategory = predictedCategory;
        this.clusterSummary = new ArmClusterSummaryImpl(cluster);

        this.dataClassesSummary = ensemble
                .stream()
                .map(Model::getPseudoPoints)
                .flatMap(List::stream)
                .map(ArmClusterSummaryImpl::new)
                .collect(Collectors.toList());

        this.clusterDataInstances = cluster.getSamples()
                .stream()
                .map(sample -> new ArmDataInstanceImpl(sample, false))
                .collect(Collectors.toList());

    }

    public ArmInterceptionContextImpl(final ImpurityBasedCluster impurityBasedCluster,
                                      final Set<Sample> labeledSamples,
                                      final List<Model> ensemble) {


        this.clusterSummary = new ArmClusterSummaryImpl(impurityBasedCluster);

        this.dataClassesSummary = ensemble
                .stream()
                .map(Model::getPseudoPoints)
                .flatMap(List::stream)
                .map(ArmClusterSummaryImpl::new)
                .collect(Collectors.toList());

        this.clusterDataInstances = impurityBasedCluster.getSamples()
                .stream()
                .map(sample -> new ArmDataInstanceImpl(sample, labeledSamples.contains(sample)))
                .collect(Collectors.toList());

        final Set<Integer> knownLabels = ensemble
                .stream()
                .map(Model::getKnownLabels)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        if (knownLabels.contains(impurityBasedCluster.getMostFrequentLabel())) {
            this.predictedCategory = ArmClusterCategory.KNOWN;
        } else {
            this.predictedCategory = ArmClusterCategory.NOVELTY;
        }

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
}
