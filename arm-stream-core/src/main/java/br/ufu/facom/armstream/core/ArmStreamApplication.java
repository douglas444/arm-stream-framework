package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.text.MessageFormat.format;

public class ArmStreamApplication {

    private static final String ERROR_INSTANTIATING = "Error instantiating {0}";
    private static final String ERROR_RUNNING = "Error running {0}";
    private static final String ERROR_KILLING = "Error killing {0}";

    private final ArmBaseClassifier baseClassifier;
    private final ArmMetaCategorizer metaCategorizer;
    private final ArmActiveCategorizer activeCategorizer;

    public ArmStreamApplication(final Class<ArmBaseClassifier> baseClassifierClass,
                                final Class<ArmMetaCategorizer> metaCategorizerClass,
                                final Class<ArmActiveCategorizer> activeCategorizerClass) throws ArmStreamException {

        try {
            this.baseClassifier = baseClassifierClass.getConstructor().newInstance();
        } catch(Exception e) {
            throw new ArmStreamException(format(ERROR_INSTANTIATING, baseClassifierClass.getSimpleName()), e);
        }

        try {
            this.metaCategorizer = metaCategorizerClass.getConstructor().newInstance();
        } catch(Exception e) {
            throw new ArmStreamException(format(ERROR_INSTANTIATING, baseClassifierClass.getSimpleName()), e);
        }

        try {
            this.activeCategorizer = activeCategorizerClass.getConstructor().newInstance();
        } catch(Exception e) {
            throw new ArmStreamException(format(ERROR_INSTANTIATING, baseClassifierClass.getSimpleName()), e);
        }

    }

    private EvaluationSummary run() throws ArmStreamException {

        final List<InterceptionLog> logs = new ArrayList<>();

        final ArmInterceptor interceptor = (context) -> {

            final ArmClusterCategory trueCategory = Oracle.calculateTrueCategory(context.getClusterDataInstances(),
                    context.getDataClassesSummary());

            final ArmClusterCategory basePrediction = context.getPredictedCategory();
            final ArmClusterCategory metaPrediction = this.metaCategorizer.categorize(context);
            final ArmClusterCategory activePrediction;
            final ArmInterceptionResult armInterceptionResult;

            if (metaPrediction == basePrediction) {

                activePrediction = null;
                armInterceptionResult = new ArmInterceptionResult() {
                    public ArmClusterCategory getPrediction() { return metaPrediction; }
                    public List<ArmDataInstance> getLabeledDataInstances() { return Collections.emptyList(); }
                };

            } else {

                armInterceptionResult = this.activeCategorizer.categorize(context);
                activePrediction = armInterceptionResult.getPrediction();

            }

            logs.add(new InterceptionLog(trueCategory, basePrediction, metaPrediction, activePrediction));
            return armInterceptionResult;

        };

        try {
            this.baseClassifier.run(interceptor);
        } catch (Exception e) {
            throw new ArmStreamException(format(ERROR_RUNNING, ArmBaseClassifier.class.getSimpleName()), e);
        }

        return new EvaluationSummary(logs);
    }

    private void kill() throws ArmStreamException {
        try {
            this.baseClassifier.kill();
        } catch (Exception e) {
            throw new ArmStreamException(format(ERROR_KILLING, ArmBaseClassifier.class.getSimpleName()), e);
        }
    }

    private HashMap<String, String> getBaseClassifierNominalParameters() {
        return this.baseClassifier.getNominalParameters();
    }

    private HashMap<String, Double> getBaseClassifierNumericParameters() {
        return this.baseClassifier.getNumericParameters();
    }

    private HashMap<String, String> getMetaCategorizerNominalParameters() {
        return this.metaCategorizer.getNominalParameters();
    }

    private HashMap<String, Double> getMetaCategorizerNumericParameters() {
        return this.metaCategorizer.getNumericParameters();
    }

    private HashMap<String, String> getActiveCategorizerNominalParameters() {
        return this.activeCategorizer.getNominalParameters();
    }

    private HashMap<String, Double> getActiveCategorizerNumericParameters() {
        return this.activeCategorizer.getNumericParameters();
    }


}
