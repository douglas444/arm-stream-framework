package br.ufu.facom.armstream.core.interceptor;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;
import br.ufu.facom.armstream.core.evaluation.EvaluationSummary;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractInterceptor implements ArmInterceptor {

    protected final EvaluationSummary summary;

    protected AbstractInterceptor(final EvaluationSummary summary) {
        this.summary = summary;

        // Times with one-class data classes summary
        this.summary.getBuffer().put("ONE_CLASS_MODEL", 0.0);

        // True novelty clusters intercepted with one-class data classes summary
        this.summary.getBuffer().put("TRUE_NOVELTY_WHEN_ONE_CLASS_MODEL", 0.0);
    }

    @Override
    public ArmInterceptionResult intercept(final ArmInterceptionContext context) {

        if (context == null
                || context.getDataClassesSummary() == null
                || context.getClusterDataInstances() == null
                || context.getPredictedCategory() == null
                || context.getClusterSummary() == null) {

            throw new IllegalArgumentException();

        }

        final ArmClusterCategory trueCategory = calculateTrueCategory(
                context.getClusterDataInstances(),
                context.getDataClassesSummary());

        if (context.getDataClassesSummary()
                .stream()
                .map(ArmClusterSummary::getLabel)
                .distinct()
                .count() < 2) {

            double oneClassModelCount = this.summary.getBuffer().get("ONE_CLASS_MODEL");
            this.summary.getBuffer().put("ONE_CLASS_MODEL", oneClassModelCount + 1);

            if (trueCategory == ArmClusterCategory.NOVELTY) {
                double noveltyCount = this.summary.getBuffer().get("TRUE_NOVELTY_WHEN_ONE_CLASS_MODEL");
                this.summary.getBuffer().put("TRUE_NOVELTY_WHEN_ONE_CLASS_MODEL", noveltyCount + 1);
            }
        }

        return this.intercept(context, trueCategory);

    }

    private static ArmClusterCategory calculateTrueCategory(final List<ArmDataInstance> clusterDataInstances,
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

    public EvaluationSummary getSummary() {
        return this.summary;
    }

    protected abstract ArmInterceptionResult intercept(ArmInterceptionContext context, ArmClusterCategory trueCategory);


}
