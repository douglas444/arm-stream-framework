package br.ufu.facom.armstream.exp.experiments;

import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.modules.ArmBaseClassifier;
import br.ufu.facom.armstream.core.ArmStream;
import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.core.evaluation.EvaluationSummary;
import br.ufu.facom.armstream.exp.util.FileUtil;
import br.ufu.facom.armstream.ref.categorizers.active.Dummy;
import br.ufu.facom.armstream.ref.categorizers.meta.GroupedErrorEstimateCategorizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThresholdFactorAnalysis {

    public static void executeWithLooseIntegration(final ArmBaseClassifier baseClassifier,
                                                   final GroupedErrorEstimateCategorizer[] refCategorizers,
                                                   final String outputDestination,
                                                   final String outputFileName)
            throws ArmStreamException, FileNotFoundException {

        final double increment = 0.1;
        final int totalAxisTicks = (int) (1 / increment);
        final int totalOfCategorizers = refCategorizers.length * totalAxisTicks;

        final GroupedErrorEstimateCategorizer[] categorizers = new GroupedErrorEstimateCategorizer[refCategorizers.length * totalAxisTicks];
        final double[] thresholdFactors = new double[totalAxisTicks];

        for (int i = 0; i < totalOfCategorizers; i += refCategorizers.length) {

            final int currentAxisTick = i / refCategorizers.length;
            thresholdFactors[currentAxisTick] = increment * (currentAxisTick + 1);

            for (int j = 0; j < refCategorizers.length; ++j) {
                categorizers[i + j] = refCategorizers[j].withThresholdFactor(thresholdFactors[currentAxisTick]);
            }
        }

        final List<HashMap<String, String>> baseClassifierPeekedData = new ArrayList<>();

        final EvaluationSummary summary = ArmStream.runLooseIntegration(
                baseClassifier,
                categorizers,
                new ArmActiveCategorizer[]{new Dummy()},
                Util.configurePeeker(baseClassifierPeekedData));

        final double[][] sensitivities = new double[totalAxisTicks][refCategorizers.length];
        final double[][] specificities = new double[totalAxisTicks][refCategorizers.length];

        for (int i = 0; i < totalOfCategorizers; i += refCategorizers.length) {

            final int currentAxisTick = i / refCategorizers.length;

            for (int j = 0; j < refCategorizers.length; ++j) {
                sensitivities[currentAxisTick][j] = summary.getMetaCategorizerConfusionMatrix()[i + j].sensitivity();
                specificities[currentAxisTick][j] = summary.getMetaCategorizerConfusionMatrix()[i + j].specificity();
            }
        }

        saveThresholdFactorAnalysisToFile(
                outputDestination,
                outputFileName,
                baseClassifier,
                refCategorizers,
                thresholdFactors,
                sensitivities,
                specificities);

    }

    public static void saveThresholdFactorAnalysisToFile(final String destination,
                                                         final String fileName,
                                                         final ArmBaseClassifier baseClassifier,
                                                         final GroupedErrorEstimateCategorizer[] groupedErrorCategorizers,
                                                         final double[] thresholdFactors,
                                                         final double[][] sensitivities,
                                                         final double[][] specificities) throws FileNotFoundException {

        final ObjectMapper mapper = new ObjectMapper();

        final ObjectNode root = mapper.createObjectNode();
        root.put("baseClassifierClassName", baseClassifier.getClass().getName());
        root.putPOJO("baseClassifierFields", baseClassifier);

        final ArrayNode groupedErrorCategorizersLevel = root.withArray("groupedErrorCategorizers");

        for (int i = 0; i < groupedErrorCategorizers.length; ++i) {

            final ObjectNode groupedErrorCategorizerRoot = mapper.createObjectNode();
            groupedErrorCategorizerRoot.put("groupedErrorCategorizerClassName", groupedErrorCategorizers[i].getClass().getName());
            groupedErrorCategorizerRoot.putPOJO("groupedErrorCategorizerFields", groupedErrorCategorizers[i]);

            final ArrayNode results = groupedErrorCategorizerRoot.withArray("results");

            for (int j = 0; j < thresholdFactors.length; ++j) {

                final ObjectNode result = mapper.createObjectNode();
                result.put("threshold", thresholdFactors[j]);
                result.put("sensitivity", sensitivities[j][i]);
                result.put("specificity", specificities[j][i]);
                results.add(result);
            }

            groupedErrorCategorizersLevel.add(groupedErrorCategorizerRoot);
        }

        FileUtil.writeToFile(root.toPrettyString(), destination, fileName);
    }

}
