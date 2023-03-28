package br.ufu.facom.armstream.core.evaluation;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;

import java.util.Objects;

public class CategorizationConfusionMatrix {

    private int trueKnown;
    private int falseKnown;
    private int trueNovelty;
    private int falseNovelty;

    public CategorizationConfusionMatrix() {
        this.trueKnown = 0;
        this.falseKnown = 0;
        this.trueNovelty = 0;
        this.falseNovelty = 0;
    }

    public void increment(final ArmClusterCategory trueCategory, final ArmClusterCategory predictedCategory) {

        Objects.requireNonNull(predictedCategory);

        if (predictedCategory == ArmClusterCategory.KNOWN) {

            if (predictedCategory == trueCategory) {
                ++this.trueKnown;
            } else {
                ++this.falseKnown;
            }

        } else {

            if (predictedCategory == trueCategory) {
                ++this.trueNovelty;
            } else {
                ++this.falseNovelty;
            }
        }
    }

    public double sensitivity() {
        return this.trueNovelty / (double) (this.trueNovelty + this.falseKnown);
    }

    public double specificity() {
        return this.trueKnown / (double) (this.trueKnown + this.falseNovelty);
    }

    //Getters

    public int getTrueKnown() {
        return trueKnown;
    }

    public int getFalseKnown() {
        return falseKnown;
    }

    public int getTrueNovelty() {
        return trueNovelty;
    }

    public int getFalseNovelty() {
        return falseNovelty;
    }
}
