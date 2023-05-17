package br.ufu.facom.armstream.http;

import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArmDataInstanceDTO implements ArmDataInstance {

    @JsonProperty("attributes")
    private double[] attributes;
    @JsonProperty("true_label")
    private int trueLabel;
    @JsonProperty("true_label_available")
    private boolean trueLabelAvailable;

    public ArmDataInstanceDTO() {
    }

    public ArmDataInstanceDTO(double[] attributes, int trueLabel, boolean trueLabelAvailable) {
        this.attributes = attributes;
        this.trueLabel = trueLabel;
        this.trueLabelAvailable = trueLabelAvailable;
    }

    @Override
    public double[] getAttributes() {
        return attributes;
    }

    public void setAttributes(double[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public int getTrueLabel() {
        return trueLabel;
    }

    public void setTrueLabel(int trueLabel) {
        this.trueLabel = trueLabel;
    }

    @Override
    public boolean isTrueLabelAvailable() {
        return trueLabelAvailable;
    }

    public void setTrueLabelAvailable(boolean trueLabelAvailable) {
        this.trueLabelAvailable = trueLabelAvailable;
    }
}
