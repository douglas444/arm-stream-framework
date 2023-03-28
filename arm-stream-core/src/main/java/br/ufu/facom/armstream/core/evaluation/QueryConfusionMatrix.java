package br.ufu.facom.armstream.core.evaluation;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;

public class QueryConfusionMatrix {

    private int trueAgreement;
    private int falseAgreement;
    private int trueDisagreement;
    private int falseDisagreement;

    public QueryConfusionMatrix() {
        this.trueAgreement = 0;
        this.falseAgreement = 0;
        this.trueDisagreement = 0;
        this.falseDisagreement = 0;
    }

    public void increment(final ArmClusterCategory trueCategory,
                          final ArmClusterCategory basePrediction,
                          final ArmClusterCategory metaPrediction) {

        if (basePrediction == metaPrediction) {

            if (basePrediction == trueCategory) {
                ++this.trueAgreement;
            } else {
                ++this.falseAgreement;
            }

        } else {

            if (basePrediction == trueCategory) {
                ++this.falseDisagreement;
            } else {
                ++this.trueDisagreement;
            }
        }
    }

    public QueryConfusionMatrix sum(final QueryConfusionMatrix matrix) {

        final QueryConfusionMatrix sum = new QueryConfusionMatrix();
        sum.trueDisagreement = this.trueDisagreement + matrix.trueDisagreement;
        sum.falseDisagreement = this.falseDisagreement + matrix.falseDisagreement;
        sum.trueAgreement = this.trueAgreement + matrix.trueAgreement;
        sum.falseAgreement = this.falseAgreement + matrix.falseAgreement;
        return sum;

    }

    public double precision() {
        return this.trueDisagreement / (double) (this.trueDisagreement + this.falseDisagreement);
    }

    public double sensitivity() {
        return this.trueDisagreement / (double) (this.trueDisagreement + this.falseAgreement);
    }

    //Getters

    public int getTrueAgreement() {
        return trueAgreement;
    }

    public int getFalseAgreement() {
        return falseAgreement;
    }

    public int getTrueDisagreement() {
        return trueDisagreement;
    }

    public int getFalseDisagreement() {
        return falseDisagreement;
    }
}
