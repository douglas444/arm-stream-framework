package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.modules.ArmBaseClassifier;
import br.ufu.facom.armstream.api.modules.ArmMetaCategorizer;
import br.ufu.facom.armstream.core.evaluation.EvaluationSummary;
import br.ufu.facom.armstream.core.interceptor.AbstractInterceptor;
import br.ufu.facom.armstream.core.interceptor.LooseInterceptor;
import br.ufu.facom.armstream.core.interceptor.TightInterceptor;

import java.util.HashMap;
import java.util.function.Consumer;

public class ArmStream {

    public static EvaluationSummary runLooseIntegration(
            final ArmBaseClassifier baseClassifier,
            final ArmMetaCategorizer[] metaCategorizers,
            final ArmActiveCategorizer[] activeCategorizers) throws ArmStreamException {

        return run(baseClassifier, new LooseInterceptor(metaCategorizers, activeCategorizers), (properties) -> {
        });
    }

    public static EvaluationSummary runLooseIntegration(
            final ArmBaseClassifier baseClassifier,
            final ArmMetaCategorizer[] metaCategorizers,
            final ArmActiveCategorizer[] activeCategorizers,
            final Consumer<HashMap<String, String>> peeker) throws ArmStreamException {

        return run(baseClassifier, new LooseInterceptor(metaCategorizers, activeCategorizers), peeker);
    }

    public static EvaluationSummary runTightIntegration(
            final ArmBaseClassifier baseClassifier,
            final ArmMetaCategorizer metaCategorizer,
            final ArmActiveCategorizer activeCategorizer) throws ArmStreamException {

        return run(baseClassifier, new TightInterceptor(metaCategorizer, activeCategorizer), (properties) -> {
        });
    }

    public static EvaluationSummary runTightIntegration(
            final ArmBaseClassifier baseClassifier,
            final ArmMetaCategorizer metaCategorizer,
            final ArmActiveCategorizer activeCategorizer,
            final Consumer<HashMap<String, String>> peeker) throws ArmStreamException {

        return run(baseClassifier, new TightInterceptor(metaCategorizer, activeCategorizer), peeker);
    }

    public static EvaluationSummary run(
            final ArmBaseClassifier baseClassifier,
            final AbstractInterceptor interceptor,
            final Consumer<HashMap<String, String>> peeker) throws ArmStreamException {

        try {
            baseClassifier.run(interceptor, peeker);
        } catch (final Exception e) {
            throw new ArmStreamException(e);
        }

        return interceptor.getSummary();
    }

}
