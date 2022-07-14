package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.ActiveCategorizer;
import br.ufu.facom.armstream.api.data.ArmClusterCategory;
import br.ufu.facom.armstream.api.MetaCategorizer;
import br.ufu.facom.armstream.api.data.InterceptionContext;
import br.ufu.facom.armstream.api.ArmInterceptor;
import br.ufu.facom.armstream.api.data.InterceptionResult;

import java.util.ArrayList;
import java.util.List;

public class InterceptorImpl implements ArmInterceptor {

    private final MetaCategorizer metaCategorizer;
    private final ActiveCategorizer activeCategorizer;
    private final List<InterceptionLog> logs;

    public InterceptorImpl(final MetaCategorizer metaCategorizer, final ActiveCategorizer activeCategorizer) {
        this.metaCategorizer = metaCategorizer;
        this.activeCategorizer = activeCategorizer;
        this.logs = new ArrayList<>();
    }

    public List<InterceptionLog> getLogs() {return logs;}

    @Override
    public InterceptionResult intercept(InterceptionContext context) {

        final ArmClusterCategory trueCategory = Oracle.calculateTrueCategory(context.getClusterDataInstances());
        final ArmClusterCategory basePrediction = context.getPredictedCategory();
        final ArmClusterCategory metaPrediction = this.metaCategorizer.categorize(context);
        final ArmClusterCategory activePrediction;
        final InterceptionResult interceptionResult;

        if (metaPrediction == basePrediction) {
            activePrediction = null;
            interceptionResult = new InterceptionResultImpl(metaPrediction, null);

        } else {
            interceptionResult = this.activeCategorizer.categorize(context);
            activePrediction = interceptionResult.getPrediction();

        }

        this.logs.add(new InterceptionLog(trueCategory, basePrediction, metaPrediction, activePrediction));
        return interceptionResult;

    }

}
