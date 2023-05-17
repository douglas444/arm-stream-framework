package br.ufu.facom.armstream.api.datastructure;

public interface ArmClusterSummary {

    double[] getCentroidAttributes();

    double getStandardDeviation();

    Integer getLabel();

}
