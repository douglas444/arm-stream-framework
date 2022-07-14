package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.ActiveCategorizer;
import br.ufu.facom.armstream.api.data.ArmClusterCategory;
import br.ufu.facom.armstream.api.MetaCategorizer;
import br.ufu.facom.armstream.api.data.ArmInterceptionContext;
import br.ufu.facom.armstream.api.ArmInterceptor;
import br.ufu.facom.armstream.api.data.ArmInterceptionResult;

import java.util.ArrayList;
import java.util.List;

public class Interceptor implements ArmInterceptor {

    private final MetaCategorizer metaCategorizer;
    private final ActiveCategorizer activeCategorizer;
    private final List<InterceptionLog> logs;

    public Interceptor(final MetaCategorizer metaCategorizer, final ActiveCategorizer activeCategorizer) {
        this.metaCategorizer = metaCategorizer;
        this.activeCategorizer = activeCategorizer;
        this.logs = new ArrayList<>();
    }

    public List<InterceptionLog> getLogs() {return logs;}

    @Override
    public ArmInterceptionResult intercept(ArmInterceptionContext context) {

        final ArmClusterCategory trueCategory = Oracle.calculateTrueCategory(context.getClusterDataInstances());
        final ArmClusterCategory basePrediction = context.getPredictedCategory();
        final ArmClusterCategory metaPrediction = this.metaCategorizer.categorize(context);
        final ArmClusterCategory activePrediction;
        final ArmInterceptionResult armInterceptionResult;

        if (metaPrediction == basePrediction) {
            activePrediction = null;
            armInterceptionResult = new InterceptionResult(metaPrediction, null);

        } else {
            armInterceptionResult = this.activeCategorizer.categorize(context);
            activePrediction = armInterceptionResult.getPrediction();

        }

        this.logs.add(new InterceptionLog(trueCategory, basePrediction, metaPrediction, activePrediction));
        return armInterceptionResult;

    }

}
