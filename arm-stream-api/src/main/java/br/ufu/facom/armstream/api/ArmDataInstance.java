package br.ufu.facom.armstream.api;

public interface ArmDataInstance {

    double[] getAttributes();
    Integer getTrueLabel();
    boolean isTrueLabelKnown();

}
