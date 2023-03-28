package br.ufu.facom.armstream.ref.echo.armstream;

import br.ufu.facom.armstream.ref.echo.ImpurityBasedCluster;
import br.ufu.facom.armstream.ref.echo.PseudoPoint;
import br.ufu.facom.armstream.ref.util.datastructures.Cluster;
import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;

public class ArmClusterSummaryImpl implements ArmClusterSummary {

    private final double[] centroidAttributes;
    private final double standardDeviation;
    private final Integer label;

    public ArmClusterSummaryImpl(final PseudoPoint pseudoPoint) {
        this.centroidAttributes = pseudoPoint.getCentroid().getX().clone();
        this.standardDeviation = pseudoPoint.calculateStandardDeviation();
        this.label = pseudoPoint.getLabel();
    }

    public ArmClusterSummaryImpl(final ImpurityBasedCluster impurityBasedCluster) {
        this.centroidAttributes = impurityBasedCluster.getCentroid().getX().clone();
        this.standardDeviation = impurityBasedCluster.calculateStandardDeviation();
        this.label = impurityBasedCluster.getMostFrequentLabel();
    }

    public ArmClusterSummaryImpl(final Cluster cluster) {
        this.centroidAttributes = cluster.calculateCentroid().getX().clone();
        this.standardDeviation = cluster.calculateStandardDeviation();
        this.label = cluster.getLabel();
    }

    @Override
    public double[] getCentroidAttributes() {
        return this.centroidAttributes;
    }

    @Override
    public double getStandardDeviation() {
        return this.standardDeviation;
    }

    @Override
    public Integer getLabel() {
        return this.label;
    }
}
