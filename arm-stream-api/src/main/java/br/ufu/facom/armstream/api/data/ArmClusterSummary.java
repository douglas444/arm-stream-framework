package br.ufu.facom.armstream.api.data;

public interface ArmClusterSummary {

    double[] getCentroidAttributes();
    double getStandardDeviation();
    Integer getLabel();

}
