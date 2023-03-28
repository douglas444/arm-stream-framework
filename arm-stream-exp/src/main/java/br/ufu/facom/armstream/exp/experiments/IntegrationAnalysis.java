package br.ufu.facom.armstream.exp.experiments;

import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.modules.ArmBaseClassifier;
import br.ufu.facom.armstream.api.modules.ArmMetaCategorizer;
import br.ufu.facom.armstream.core.ArmStream;
import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.core.evaluation.CategorizationConfusionMatrix;
import br.ufu.facom.armstream.core.evaluation.EvaluationSummary;
import br.ufu.facom.armstream.core.evaluation.QueryConfusionMatrix;
import br.ufu.facom.armstream.core.evaluation.RecoveryConfusionMatrix;
import br.ufu.facom.armstream.exp.util.FileUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntegrationAnalysis {

    public static void executeLooseIntegration(final ArmBaseClassifier baseClassifier,
                                               final ArmMetaCategorizer[] metaCategorizers,
                                               final ArmActiveCategorizer[] activeCategorizers,
                                               final String outputDestination,
                                               final String integrationReportOutputFileName,
                                               final String baseClassifierOutputFileName)
            throws ArmStreamException, FileNotFoundException {

        final List<HashMap<String, String>> baseClassifierPeekedData = new ArrayList<>();

        final EvaluationSummary summary = ArmStream.runLooseIntegration(
                baseClassifier,
                metaCategorizers,
                activeCategorizers,
                Util.configurePeeker(baseClassifierPeekedData));

        saveIntegrationReportToFile(
                outputDestination,
                integrationReportOutputFileName,
                baseClassifier,
                metaCategorizers,
                activeCategorizers,
                "loose",
                summary);

        saveBaseClassifierPeekedDataToFile(
                outputDestination,
                baseClassifierOutputFileName,
                baseClassifierPeekedData);
    }

    public static void executeTightIntegration(final ArmBaseClassifier baseClassifier,
                                               final ArmMetaCategorizer metaCategorizer,
                                               final ArmActiveCategorizer activeCategorizer,
                                               final String outputDestination,
                                               final String integrationReportOutputFileName,
                                               final String baseClassifierOutputFileName)
            throws ArmStreamException, FileNotFoundException {

        final List<HashMap<String, String>> baseClassifierPeekedData = new ArrayList<>();

        final EvaluationSummary summary = ArmStream.runTightIntegration(
                baseClassifier,
                metaCategorizer,
                activeCategorizer,
                Util.configurePeeker(baseClassifierPeekedData));

        saveIntegrationReportToFile(
                outputDestination,
                integrationReportOutputFileName,
                baseClassifier,
                new ArmMetaCategorizer[]{metaCategorizer},
                new ArmActiveCategorizer[]{activeCategorizer},
                "tight",
                summary);

        saveBaseClassifierPeekedDataToFile(
                outputDestination,
                baseClassifierOutputFileName,
                baseClassifierPeekedData);
    }



    private static void saveBaseClassifierPeekedDataToFile(final String destination,
                                                          final String fileName,
                                                          final List<HashMap<String, String>> peekedData)
            throws FileNotFoundException {

        final ObjectMapper mapper = new ObjectMapper();

        final ObjectNode root = mapper.createObjectNode();
        final ArrayNode baseClassifierOutput = root.withArray("baseClassifierOutput");
        peekedData.forEach(log -> baseClassifierOutput.add(mapper.convertValue(log, JsonNode.class)));
        FileUtil.writeToFile(root.toPrettyString(), destination, fileName);

    }

    private static void saveIntegrationReportToFile(final String destination,
                                                   final String fileName,
                                                   final ArmBaseClassifier baseClassifier,
                                                   final ArmMetaCategorizer[] metaCategorizers,
                                                   final ArmActiveCategorizer[] activeCategorizers,
                                                   final String integrationTypeFieldValue,
                                                   final EvaluationSummary summary) throws FileNotFoundException {

        final ObjectMapper mapper = new ObjectMapper();
        final ObjectNode baseCategorizerLevel = mapper.createObjectNode();

        final CategorizationConfusionMatrix baseCM = summary.getBaseCategorizerConfusionMatrix();
        baseCategorizerLevel.put("integrationType", integrationTypeFieldValue);
        baseCategorizerLevel.put("baseClassifierClassName", baseClassifier.getClass().getName());
        baseCategorizerLevel.putPOJO("baseClassifierFields", baseClassifier);
        baseCategorizerLevel.put("totalKnown", baseCM.getTrueKnown() + baseCM.getFalseNovelty());
        baseCategorizerLevel.put("totalNovelty", baseCM.getTrueNovelty() + baseCM.getFalseKnown());
        baseCategorizerLevel.put("baseCategorizerSensitivity", baseCM.sensitivity());
        baseCategorizerLevel.put("baseCategorizerSpecificity", baseCM.specificity());
        baseCategorizerLevel.putPOJO("baseClassifierCategorizationConfusionMatrix", baseCM);

        fillMetaCategorizerLevel(
                summary,
                baseCategorizerLevel.withArray("metaCategorizerLevel"),
                metaCategorizers,
                activeCategorizers);

        FileUtil.writeToFile(baseCategorizerLevel.toPrettyString(), destination, fileName);

    }

    private static void fillMetaCategorizerLevel(final EvaluationSummary summary,
                                                 final ArrayNode arrayNode,
                                                 final ArmMetaCategorizer[] metaCategorizers,
                                                 final ArmActiveCategorizer[] activeCategorizers) {

        final ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < summary.getNumberOfMetaCategorizers(); ++i) {

            final ObjectNode metaCategorizerLevel = mapper.createObjectNode();

            final QueryConfusionMatrix queryKnownCM = summary.getQueryConfusionMatrixKnown()[i];
            final QueryConfusionMatrix queryNoveltyCM = summary.getQueryConfusionMatrixNovelty()[i];
            final QueryConfusionMatrix querySumCM = queryNoveltyCM.sum(queryKnownCM);
            metaCategorizerLevel.put("metaCategorizerClassName", metaCategorizers[i].getClass().getName());
            metaCategorizerLevel.putPOJO("metaCategorizerFields", metaCategorizers[i]);
            metaCategorizerLevel.put("queriedKnown", queryKnownCM.getTrueDisagreement() + queryKnownCM.getFalseDisagreement());
            metaCategorizerLevel.put("queriedNovelty", queryNoveltyCM.getTrueDisagreement() + queryNoveltyCM.getFalseDisagreement());
            metaCategorizerLevel.put("querySensitivity", querySumCM.sensitivity());
            metaCategorizerLevel.put("queryPrecision", querySumCM.precision());
            metaCategorizerLevel.putPOJO("queryConfusionMatrixKnown", queryKnownCM);
            metaCategorizerLevel.putPOJO("queryConfusionMatrixNovelty", queryNoveltyCM);
            metaCategorizerLevel.putPOJO("queryConfusionMatrixTotal", querySumCM);

            final CategorizationConfusionMatrix metaCM = summary.getMetaCategorizerConfusionMatrix()[i];
            metaCategorizerLevel.put("metaCategorizerSensitivity", metaCM.sensitivity());
            metaCategorizerLevel.put("metaCategorizerSpecificity", metaCM.specificity());
            metaCategorizerLevel.putPOJO("metaCategorizerCategorizationConfusionMatrix", metaCM);

            fillActiveCategorizerLevel(
                    summary,
                    i,
                    metaCategorizerLevel.withArray("activeCategorizerLevel"),
                    activeCategorizers);

            arrayNode.add(metaCategorizerLevel);
        }

    }

    private static void fillActiveCategorizerLevel(final EvaluationSummary summary,
                                                   final int metaCategorizerIndex,
                                                   final ArrayNode arrayNode,
                                                   final ArmActiveCategorizer[] activeCategorizers) {

        final ObjectMapper mapper = new ObjectMapper();

        for (int j = 0; j < summary.getNumberOfActiveCategorizers(); ++j) {

            final ObjectNode activeCategorizerLevel = mapper.createObjectNode();

            final CategorizationConfusionMatrix activeCM = summary.getActiveCategorizerConfusionMatrix()[metaCategorizerIndex][j];
            activeCategorizerLevel.put("activeCategorizerClassName", activeCategorizers[j].getClass().getName());
            activeCategorizerLevel.putPOJO("activeCategorizerFields", activeCategorizers[j]);
            activeCategorizerLevel.put("activeCategorizerSensitivity", activeCM.sensitivity());
            activeCategorizerLevel.put("activeCategorizerSpecificity", activeCM.specificity());

            final RecoveryConfusionMatrix recoveryCM = summary.getRecoveryConfusionMatrix()[metaCategorizerIndex][j];
            activeCategorizerLevel.put("recoveryRate", recoveryCM.recoveryRate());
            activeCategorizerLevel.put("corruptionRate", recoveryCM.corruptionRate());
            activeCategorizerLevel.put("finalCategorizationSensitivity", recoveryCM.finalSensitivity());
            activeCategorizerLevel.put("finalCategorizationSpecificity", recoveryCM.finalSpecificity());
            activeCategorizerLevel.putPOJO("recoveryConfusionMatrix", recoveryCM);

            arrayNode.add(activeCategorizerLevel);
        }

    }
}
