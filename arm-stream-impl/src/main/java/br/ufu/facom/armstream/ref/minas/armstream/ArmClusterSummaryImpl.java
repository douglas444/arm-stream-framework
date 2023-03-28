package br.ufu.facom.armstream.ref.minas.armstream;

import br.ufu.facom.armstream.ref.minas.MicroCluster;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;

public class ArmClusterSummaryImpl implements ArmClusterSummary {

    private final double[] centroidAttributes;
    private final double standardDeviation;
    private final Integer label;

    public ArmClusterSummaryImpl(final MicroCluster microCluster) {
        this.centroidAttributes = microCluster.calculateCentroid().getX().clone();
        this.standardDeviation = microCluster.calculateStandardDeviation();
        this.label = microCluster.getLabel();
    }

    @Override
    public double[] getCentroidAttributes() {
        return centroidAttributes;
    }

    @Override
    public double getStandardDeviation() {
        return standardDeviation;
    }

    @Override
    public Integer getLabel() {
        return label;
    }

}
