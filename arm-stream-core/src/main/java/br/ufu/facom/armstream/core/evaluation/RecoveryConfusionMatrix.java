package br.ufu.facom.armstream.core.evaluation;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;

import java.util.Objects;

public class RecoveryConfusionMatrix {

    private final CategorizationConfusionMatrix base;

    private int recoveredKnown;
    private int unrecoveredKnown;
    private int corruptedKnown;
    private int uncorruptedKnown;

    private int recoveredNovelty;
    private int unrecoveredNovelty;
    private int corruptedNovelty;
    private int uncorruptedNovelty;

    public RecoveryConfusionMatrix(final CategorizationConfusionMatrix base) {

        this.base = base;

        this.unrecoveredKnown = 0;
        this.corruptedKnown = 0;
        this.uncorruptedKnown = 0;

        this.recoveredNovelty = 0;
        this.unrecoveredNovelty = 0;
        this.corruptedNovelty = 0;
        this.uncorruptedNovelty = 0;

    }

    void increment(final ArmClusterCategory trueCategory,
                   final ArmClusterCategory basePrediction,
                   final ArmClusterCategory metaPrediction,
                   final ArmClusterCategory activePrediction) {

        Objects.requireNonNull(trueCategory);

        if (basePrediction != metaPrediction) {

            if (metaPrediction == trueCategory) {

                if (activePrediction == trueCategory) {
                    incrementRecovered(trueCategory);
                } else {
                    incrementUnrecovered(trueCategory);
                }

            } else {

                if (activePrediction == trueCategory) {
                    incrementUncorrupted(trueCategory);
                } else {
                    incrementCorrupted(trueCategory);
                }

            }

        }

    }

    private void incrementRecovered(final ArmClusterCategory trueCategory) {
        if (trueCategory == ArmClusterCategory.NOVELTY) {
            ++this.recoveredNovelty;
        } else {
            ++this.recoveredKnown;
        }
    }

    private void incrementUnrecovered(final ArmClusterCategory trueCategory) {
        if (trueCategory == ArmClusterCategory.NOVELTY) {
            ++this.unrecoveredNovelty;
        } else {
            ++this.unrecoveredKnown;
        }
    }

    private void incrementCorrupted(final ArmClusterCategory trueCategory) {
        if (trueCategory == ArmClusterCategory.NOVELTY) {
            ++this.corruptedNovelty;
        } else {
            ++this.corruptedKnown;
        }
    }

    private void incrementUncorrupted(final ArmClusterCategory trueCategory) {
        if (trueCategory == ArmClusterCategory.NOVELTY) {
            ++this.uncorruptedNovelty;
        } else {
            ++this.uncorruptedKnown;
        }
    }

    private int recovered() {
        return this.recoveredKnown + this.recoveredNovelty;
    }

    private int corrupted() {
        return this.corruptedKnown + this.corruptedNovelty;
    }

    public double recoveryRate() {
        return this.recovered() / (double) (this.base.getFalseKnown() + this.base.getFalseNovelty());
    }

    public double corruptionRate() {
        return this.corrupted() / (double) (this.base.getTrueKnown() + this.base.getTrueNovelty());
    }

    public double finalSensitivity() {
        final int hits = this.base.getTrueNovelty() + this.recoveredNovelty - this.corruptedNovelty;
        return hits / (double) (this.base.getTrueNovelty() + this.base.getFalseKnown());
    }

    public double finalSpecificity() {
        final int hits = this.base.getTrueKnown() + this.recoveredKnown - this.corruptedKnown;
        return hits / (double) (this.base.getTrueKnown() + this.base.getFalseNovelty());
    }

    //Getters

    public int getRecoveredKnown() {
        return recoveredKnown;
    }

    public int getUnrecoveredKnown() {
        return unrecoveredKnown;
    }

    public int getCorruptedKnown() {
        return corruptedKnown;
    }

    public int getUncorruptedKnown() {
        return uncorruptedKnown;
    }

    public int getRecoveredNovelty() {
        return recoveredNovelty;
    }

    public int getUnrecoveredNovelty() {
        return unrecoveredNovelty;
    }

    public int getCorruptedNovelty() {
        return corruptedNovelty;
    }

    public int getUncorruptedNovelty() {
        return uncorruptedNovelty;
    }
}
