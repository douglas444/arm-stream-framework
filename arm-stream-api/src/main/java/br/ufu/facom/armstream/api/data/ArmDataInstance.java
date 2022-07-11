package br.ufu.facom.armstream.api.data;

public interface ArmDataInstance {

    double[] getAttributes();
    Integer getTrueLabel();
    boolean isTrueLabelKnown();

}
