package br.ufu.facom.armstream.ref.categorizers.active;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;
import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class MajorityOf implements ArmActiveCategorizer {

    @Override
    public ArmInterceptionResult categorize(final ArmInterceptionContext context) {

        final Set<Integer> knownLabels = context
                .getDataClassesSummary()
                .stream()
                .map(ArmClusterSummary::getLabel)
                .collect(Collectors.toSet());

        final List<ArmDataInstance> preLabeledDataInstances = context
                .getClusterDataInstances()
                .stream()
                .filter(ArmDataInstance::isTrueLabelAvailable)
                .collect(Collectors.toList());

        final List<ArmDataInstance> labeledDataInstances = this.selectDataInstancesToBeLabeled(context);

        final ArmClusterCategory predictedCategory = calculateMajorityCategory(
                labeledDataInstances,
                preLabeledDataInstances,
                knownLabels);

        return new ArmInterceptionResult() {
            @Override
            public ArmClusterCategory getPrediction() {
                return predictedCategory;
            }

            @Override
            public List<ArmDataInstance> getLabeledDataInstances() {
                return labeledDataInstances;
            }
        };

    }

    protected abstract List<ArmDataInstance> selectDataInstancesToBeLabeled(ArmInterceptionContext context);

    static ArmClusterCategory calculateMajorityCategory(final List<ArmDataInstance> labeledDataInstances,
                                                        final List<ArmDataInstance> preLabeledDataInstances,
                                                        final Set<Integer> knownLabels) {

        int sentence = labeledDataInstances.stream()
                .map(dataInstance -> !knownLabels.contains(dataInstance.getTrueLabel()))
                .map(isNovel -> isNovel ? 1 : -1)
                .reduce(0, Integer::sum);

        sentence += preLabeledDataInstances.stream()
                .map(dataInstance -> !knownLabels.contains(dataInstance.getTrueLabel()))
                .map(isNovel -> isNovel ? 1 : -1)
                .reduce(0, Integer::sum);

        if (sentence / (double) labeledDataInstances.size() >= 0) {
            return ArmClusterCategory.NOVELTY;
        } else {
            return ArmClusterCategory.KNOWN;
        }
    }

}
