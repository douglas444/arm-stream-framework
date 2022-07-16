package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.ArmClusterCategory;

import java.util.LinkedHashMap;
import java.util.List;

public class EvaluationSummary {

    private final int metaTrueKnown;
    private final int metaFalseKnown;
    private final int metaTrueNovelty;
    private final int metaFalseNovelty;

    private final int baseTrueKnown;
    private final int baseFalseKnown;
    private final int baseTrueNovelty;
    private final int baseFalseNovelty;

    private final int activeTrueKnown;
    private final int activeFalseKnown;
    private final int activeTrueNovelty;
    private final int activeFalseNovelty;

    private final int queryTruePositive;
    private final int queryFalsePositive;
    private final int queryTrueNegative;
    private final int queryFalseNegative;

    private final int recoveredNovelty;
    private final int recoveredKnown;
    private final int unrecoveredNovelty;
    private final int unrecoveredKnown;

    private final int corruptedNovelty;
    private final int corruptedKnown;
    private final int uncorruptedNovelty;
    private final int uncorruptedKnown;

    public EvaluationSummary(final List<InterceptionLog> logs) {

        if (logs.isEmpty()) {
            throw new IllegalArgumentException("Cannot build evaluation summary. No interception log was registered.");
        }

        this.baseTrueKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .filter(log -> log.getBasePrediction() == ArmClusterCategory.KNOWN)
                .count();

        this.baseFalseKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .filter(log -> log.getBasePrediction() == ArmClusterCategory.KNOWN)
                .count();

        this.baseTrueNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .filter(log -> log.getBasePrediction() == ArmClusterCategory.NOVELTY)
                .count();

        this.baseFalseNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .filter(log -> log.getBasePrediction() == ArmClusterCategory.NOVELTY)
                .count();

        this.metaTrueKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == log.getMetaPrediction())
                .filter(log -> log.getMetaPrediction() == ArmClusterCategory.KNOWN)
                .count();

        this.metaFalseKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() != log.getMetaPrediction())
                .filter(log -> log.getMetaPrediction() == ArmClusterCategory.KNOWN)
                .count();

        this.metaTrueNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == log.getMetaPrediction())
                .filter(log -> log.getMetaPrediction() == ArmClusterCategory.NOVELTY)
                .count();

        this.metaFalseNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() != log.getMetaPrediction())
                .filter(log -> log.getMetaPrediction() == ArmClusterCategory.NOVELTY)
                .count();

        this.activeTrueKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == log.getActivePrediction())
                .filter(log -> log.getActivePrediction() == ArmClusterCategory.KNOWN)
                .count();

        this.activeFalseKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() != log.getActivePrediction())
                .filter(log -> log.getActivePrediction() == ArmClusterCategory.KNOWN)
                .count();

        this.activeTrueNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == log.getActivePrediction())
                .filter(log -> log.getActivePrediction() == ArmClusterCategory.NOVELTY)
                .count();

        this.activeFalseNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() != log.getActivePrediction())
                .filter(log -> log.getActivePrediction() == ArmClusterCategory.NOVELTY)
                .count();

        this.queryTruePositive = (int) logs.stream()
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .count();

        this.queryFalsePositive = (int) logs.stream()
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .count();

        this.queryTrueNegative = (int) logs.stream()
                .filter(log -> log.getMetaPrediction() == log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .count();

        this.queryFalseNegative = (int) logs.stream()
                .filter(log -> log.getMetaPrediction() == log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .count();

        this.recoveredKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.KNOWN)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getActivePrediction())
                .count();

        this.recoveredNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.NOVELTY)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getActivePrediction())
                .count();

        this.unrecoveredKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.KNOWN)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getActivePrediction())
                .count();

        this.unrecoveredNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.NOVELTY)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getActivePrediction())
                .count();

        this.corruptedKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.KNOWN)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getActivePrediction())
                .count();

        this.corruptedNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.NOVELTY)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .filter(log -> log.getTrueCategory() != log.getActivePrediction())
                .count();

        this.uncorruptedKnown = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.KNOWN)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getActivePrediction())
                .count();

        this.uncorruptedNovelty = (int) logs.stream()
                .filter(log -> log.getTrueCategory() == ArmClusterCategory.NOVELTY)
                .filter(log -> log.getMetaPrediction() != log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getBasePrediction())
                .filter(log -> log.getTrueCategory() == log.getActivePrediction())
                .count();
        
    }

    public double calculateBaseCategorizerSensitivity() {
        return this.baseTrueNovelty / (double) (this.baseTrueNovelty + this.baseFalseKnown);
    }

    public double calculateBaseCategorizerSpecificity() {
        return this.baseTrueKnown / (double) (this.baseTrueKnown + this.baseFalseNovelty);
    }

    public double calculateMetaCategorizerSensitivity() {
        return this.metaTrueNovelty / (double) (this.metaTrueNovelty + this.metaFalseKnown);
    }

    public double calculateMetaCategorizerSpecificity() {
        return this.metaTrueKnown / (double) (this.metaTrueKnown + this.metaFalseNovelty);
    }

    public double calculateActiveCategorizerSensitivity() {
        return this.activeTrueNovelty / (double) (this.activeTrueNovelty + this.activeFalseKnown);
    }

    public double calculateActiveCategorizerSpecificity() {
        return this.activeTrueKnown / (double) (this.activeTrueKnown + this.activeFalseNovelty);
    }

    public double calculateQueryingPrecision() {
        return this.queryTruePositive / (double) (this.queryTruePositive + this.queryFalsePositive);
    }

    public double calculateQueryingSensitivity() {
        return this.queryTruePositive / (double) (this.queryTruePositive + this.queryFalseNegative);
    }

    public double calculateRecoveryRate() {
        return (this.recoveredKnown + this.recoveredNovelty) / (double) (this.baseFalseKnown + this.baseFalseNovelty);
    }

    public double calculateCorruptionRate() {
        return (this.corruptedKnown + this.corruptedNovelty) / (double) (this.baseTrueKnown + this.baseTrueNovelty);
    }

    public double calculateFinalCategorizerSensitivity() {
        return (this.baseTrueNovelty + (this.recoveredNovelty - this.corruptedNovelty))
                / (double) (this.baseTrueNovelty + this.baseFalseKnown);
    }

    public double calculateFinalCategorizerSpecificity() {
        return (this.baseTrueKnown + (this.recoveredKnown - this.corruptedKnown))
                / (double) (this.baseTrueKnown + this.baseFalseNovelty);
    }

    public double calculateNumberOfInterceptedTrueNovelties() {
        return this.metaTrueNovelty + this.metaFalseKnown;
    }

    public double calculateNumberOfInterceptedTrueKnown() {
        return this.metaTrueKnown + this.metaFalseNovelty;
    }

    public double calculateNumberOfQueriedTrueNovelties() {
        return this.activeTrueNovelty + this.activeFalseKnown;
    }

    public double calculateNumberOfQueriedTrueKnown() {
        return this.activeTrueKnown + this.activeFalseNovelty;
    }

    public String clusterQueryingConfusionMatrixToString() {
        return "{" +
                "goodAgreement=" + this.queryTruePositive +
                ", badAgreement=" + this.queryFalsePositive +
                ", goodDisagreement=" + this.queryTrueNegative +
                ", badDisagreement=" + this.queryFalseNegative +
                "}";
    }

    public String baseCategorizerConfusionMatrixToString() {
        return "{" +
                "trueKnown=" + this.baseTrueKnown +
                ", falseKnown=" + this.baseFalseKnown +
                ", trueNovelty=" + this.baseTrueNovelty +
                ", falseNovelty=" + this.baseFalseNovelty +
                "}";
    }

    public String metaCategorizerConfusionMatrixToString() {
        return "{" +
                "trueKnown=" + this.metaTrueKnown +
                ", falseKnown=" + this.metaFalseKnown +
                ", trueNovelty=" + this.metaTrueNovelty +
                ", falseNovelty=" + this.metaFalseNovelty +
                "}";
    }

    public String activeCategorizerConfusionMatrixToString() {
        return "{" +
                "trueKnown=" + this.activeTrueKnown +
                ", falseKnown=" + this.activeFalseKnown +
                ", trueNovelty=" + this.activeTrueNovelty +
                ", falseNovelty=" + this.activeFalseNovelty +
                "}";
    }

    public String frameworkImpactConfusionMatrixToString() {
        return "{" +
                "recovered=" + this.recoveredKnown + this.recoveredNovelty +
                ", unrecovered=" + this.unrecoveredKnown + this.unrecoveredNovelty +
                ", corrupted=" + this.corruptedKnown + this.corruptedNovelty +
                ", uncorrupted=" + this.uncorruptedKnown + this.uncorruptedNovelty +
                "}";
    }

    @Override
    public String toString() {

        LinkedHashMap<String, Double> statisticByName = getStatisticByName();

        StringBuilder builder = new StringBuilder();
        statisticByName.forEach((name, statistic) -> builder.append(name).append(": ").append(statistic).append("\n"));

        builder.append("Base-categorizer confusion matrix: ")
                .append(this.baseCategorizerConfusionMatrixToString()).append("\n");
        builder.append("Meta-categorizer confusion matrix: ")
                .append(this.metaCategorizerConfusionMatrixToString()).append("\n");
        builder.append("Active-categorizer confusion matrix: ")
                .append(this.activeCategorizerConfusionMatrixToString()).append("\n");
        builder.append("Cluster querying confusion matrix: ")
                .append(this.clusterQueryingConfusionMatrixToString()).append("\n");
        builder.append("Framework impact confusion matrix: ")
                .append(this.frameworkImpactConfusionMatrixToString()).append("\n");

        return builder.toString();

    }

    public LinkedHashMap<String, Double> getStatisticByName() {
        final LinkedHashMap<String, Double> statisticsByName = new LinkedHashMap<>();
        statisticsByName.put("# Known", calculateNumberOfInterceptedTrueKnown());
        statisticsByName.put("# Novelty", calculateNumberOfInterceptedTrueNovelties());
        statisticsByName.put("# Queried known", calculateNumberOfQueriedTrueKnown());
        statisticsByName.put("# Queried novelty", calculateNumberOfQueriedTrueNovelties());
        statisticsByName.put("Base-categorizer CSe", calculateBaseCategorizerSensitivity());
        statisticsByName.put("Base-categorizer CSp", calculateBaseCategorizerSpecificity());
        statisticsByName.put("Meta-categorizer CSe", calculateMetaCategorizerSensitivity());
        statisticsByName.put("Meta-categorizer CSp", calculateMetaCategorizerSpecificity());
        statisticsByName.put("Active-categorizer CSe", calculateActiveCategorizerSensitivity());
        statisticsByName.put("Active-categorizer CSp", calculateActiveCategorizerSpecificity());
        statisticsByName.put("QSe", calculateQueryingSensitivity());
        statisticsByName.put("QPr", calculateQueryingPrecision());
        statisticsByName.put("RecR", calculateRecoveryRate());
        statisticsByName.put("CorrR", calculateCorruptionRate());
        statisticsByName.put("FinalCSe", calculateFinalCategorizerSensitivity());
        statisticsByName.put("FinalCSp", calculateFinalCategorizerSpecificity());
        return statisticsByName;

    }
}
