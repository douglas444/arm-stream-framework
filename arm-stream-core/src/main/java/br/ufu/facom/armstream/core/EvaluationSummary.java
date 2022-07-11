package br.ufu.facom.armstream.core;

import br.ufu.facom.armstream.api.data.ArmClusterCategory;

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
            throw new IllegalArgumentException("Cannot calculate measures. No log was registered.");
        }

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
    
}
