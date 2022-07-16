package br.ufu.facom.armstream.api;

public interface ArmClusterSummary {

    double[] getCentroidAttributes();
    double getStandardDeviation();
    Integer getLabel();

}
