package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.ArmClusterCategory;
import br.ufu.facom.armstream.api.ArmClusterSummary;
import br.ufu.facom.armstream.api.ArmDataInstance;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Oracle {

    static ArmClusterCategory calculateTrueCategory(final List<ArmDataInstance> clusterDataInstances,
                                                    final List<ArmClusterSummary> dataClassesSummary) {

        final Set<Integer> knownLabels = dataClassesSummary.stream()
                .map(ArmClusterSummary::getLabel)
                .collect(Collectors.toSet());

        final int sentence = clusterDataInstances.stream()
                .map(dataInstance -> knownLabels.contains(dataInstance.getTrueLabel()))
                .map(isKnown -> isKnown ? 1 : -1)
                .reduce(0, Integer::sum);

        if (sentence / (double) clusterDataInstances.size() >= 0) {
            return ArmClusterCategory.KNOWN;
        } else {
            return ArmClusterCategory.NOVELTY;
        }

    }

}
