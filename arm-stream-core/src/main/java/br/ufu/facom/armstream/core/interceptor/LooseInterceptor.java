package br.ufu.facom.armstream.core.interceptor;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;
import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.modules.ArmMetaCategorizer;
import br.ufu.facom.armstream.core.evaluation.EvaluationSummary;

public class LooseInterceptor extends AbstractInterceptor {

    private final ArmMetaCategorizer[] metaCategorizers;
    private final ArmActiveCategorizer[] activeCategorizers;

    public LooseInterceptor(final ArmMetaCategorizer[] metaCategorizers,
                            final ArmActiveCategorizer[] activeCategorizers) {

        super(new EvaluationSummary(metaCategorizers.length, activeCategorizers.length));
        this.metaCategorizers = metaCategorizers;
        this.activeCategorizers = activeCategorizers;
    }

    @Override
    public ArmInterceptionResult intercept(final ArmInterceptionContext context, final ArmClusterCategory trueCategory) {

        final ArmClusterCategory basePrediction = context.getPredictedCategory();
        final ArmClusterCategory[] metaPredictions = new ArmClusterCategory[this.metaCategorizers.length];
        final ArmClusterCategory[][] activePredictions = new ArmClusterCategory[this.metaCategorizers.length][this.activeCategorizers.length];

        for (int i = 0; i < this.metaCategorizers.length; ++i) {

            metaPredictions[i] = this.metaCategorizers[i].categorize(context);

            for (int j = 0; j < this.activeCategorizers.length; ++j) {

                if (metaPredictions[i] != basePrediction) {
                    final ArmInterceptionResult result = this.activeCategorizers[j].categorize(context);
                    activePredictions[i][j] = result.getPrediction();
                } else {
                    activePredictions[i][j] = null;
                }
            }
        }

        super.summary.update(trueCategory, basePrediction, metaPredictions, activePredictions);

        return new InterceptionResult(basePrediction);

    }

}
