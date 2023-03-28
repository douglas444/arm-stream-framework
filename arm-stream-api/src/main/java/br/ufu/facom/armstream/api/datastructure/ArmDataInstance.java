package br.ufu.facom.armstream.api.datastructure;

public interface ArmDataInstance {

    double[] getAttributes();
    int getTrueLabel();
    boolean isTrueLabelAvailable();

}
