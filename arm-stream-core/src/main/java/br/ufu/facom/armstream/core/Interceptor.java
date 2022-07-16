package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.ArmClusterCategory;
import br.ufu.facom.armstream.api.ArmMetaCategorizer;
import br.ufu.facom.armstream.api.ArmInterceptionContext;
import br.ufu.facom.armstream.api.ArmInterceptor;
import br.ufu.facom.armstream.api.ArmInterceptionResult;

import java.util.ArrayList;
import java.util.List;

public class Interceptor implements ArmInterceptor {

    private final ArmMetaCategorizer armMetaCategorizer;
    private final ArmActiveCategorizer armActiveCategorizer;
    private final List<InterceptionLog> logs;

    public Interceptor(final ArmMetaCategorizer armMetaCategorizer, final ArmActiveCategorizer armActiveCategorizer) {
        this.armMetaCategorizer = armMetaCategorizer;
        this.armActiveCategorizer = armActiveCategorizer;
        this.logs = new ArrayList<>();
    }

    public List<InterceptionLog> getLogs() {return logs;}

    @Override
    public ArmInterceptionResult intercept(ArmInterceptionContext context) {

        final ArmClusterCategory trueCategory = Oracle.calculateTrueCategory(context.getClusterDataInstances());
        final ArmClusterCategory basePrediction = context.getPredictedCategory();
        final ArmClusterCategory metaPrediction = this.armMetaCategorizer.categorize(context);
        final ArmClusterCategory activePrediction;
        final ArmInterceptionResult armInterceptionResult;

        if (metaPrediction == basePrediction) {
            activePrediction = null;
            armInterceptionResult = new InterceptionResult(metaPrediction, null);

        } else {
            armInterceptionResult = this.armActiveCategorizer.categorize(context);
            activePrediction = armInterceptionResult.getPrediction();

        }

        this.logs.add(new InterceptionLog(trueCategory, basePrediction, metaPrediction, activePrediction));
        return armInterceptionResult;

    }

}
