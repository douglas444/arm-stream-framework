package br.ufu.facom.armstream.core.interceptor;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;
import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.modules.ArmMetaCategorizer;
import br.ufu.facom.armstream.core.evaluation.EvaluationSummary;

public class TightInterceptor extends AbstractInterceptor {

    private final ArmMetaCategorizer metaCategorizer;
    private final ArmActiveCategorizer activeCategorizer;

    public TightInterceptor(final ArmMetaCategorizer metaCategorizer, final ArmActiveCategorizer activeCategorizer) {
        super(new EvaluationSummary(1, 1));
        this.metaCategorizer = metaCategorizer;
        this.activeCategorizer = activeCategorizer;
    }

    @Override
    public ArmInterceptionResult intercept(final ArmInterceptionContext context, final ArmClusterCategory trueCategory) {

        final ArmClusterCategory basePrediction = context.getPredictedCategory();
        final ArmClusterCategory metaPrediction = this.metaCategorizer.categorize(context);
        final ArmClusterCategory activePrediction;

        final ArmInterceptionResult armInterceptionResult;

        if (metaPrediction == basePrediction) {
            activePrediction = null;
            armInterceptionResult = new InterceptionResult(metaPrediction);
        } else {
            armInterceptionResult = this.activeCategorizer.categorize(context);
            activePrediction = armInterceptionResult.getPrediction();
        }

        super.summary.update(
                trueCategory,
                basePrediction,
                new ArmClusterCategory[]{metaPrediction},
                new ArmClusterCategory[][]{{activePrediction}});

        return armInterceptionResult;

    }

}
