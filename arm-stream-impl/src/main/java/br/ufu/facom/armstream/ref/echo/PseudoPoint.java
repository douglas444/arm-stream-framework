package br.ufu.facom.armstream.ref.echo;

import br.ufu.facom.armstream.ref.util.datastructures.Cluster;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.List;

public class PseudoPoint {

    private final Sample centroid;
    private final double radius;
    private final int totalNumberOfLabeledSamples;
    private final int numberOfSampleForMostFrequentLabel;
    private final int label;

    public PseudoPoint(final Cluster cluster, final int label) {

        cluster.getSamples().forEach(sample -> sample.setId(null));

        this.centroid = cluster.calculateCentroid();
        this.radius = 2 * cluster.calculateStandardDeviation();
        this.totalNumberOfLabeledSamples = 0;
        this.label = label;
        this.numberOfSampleForMostFrequentLabel = 0;

    }

    public PseudoPoint(final ImpurityBasedCluster cluster) {

        cluster.getSamples().forEach(sample -> sample.setId(null));

        this.centroid = cluster.getCentroid();
        this.radius = 2 * cluster.calculateStandardDeviation();
        this.totalNumberOfLabeledSamples = cluster.getNumberOfLabeledSamples();
        this.label = cluster.getMostFrequentLabel();
        this.numberOfSampleForMostFrequentLabel = cluster.getSamplesByLabel().get(this.label).size();

    }

    static PseudoPoint getClosestPseudoPoint(final Sample sample, final List<PseudoPoint> pseudoPoints) {

        if (pseudoPoints.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return pseudoPoints
                .stream()
                .min((pseudoPoint1, pseudoPoint2) -> {
                    final double d1 = pseudoPoint1.getCentroid().distance(sample);
                    final double d2 = pseudoPoint2.getCentroid().distance(sample);
                    return Double.compare(d1, d2);
                })
                .orElse(pseudoPoints.get(0));
    }

    double calculatePurity() {
        return (double) this.numberOfSampleForMostFrequentLabel / this.totalNumberOfLabeledSamples;
    }

    public double calculateStandardDeviation() {
        return this.radius / 2;
    }

    public Sample getCentroid() {
        return centroid;
    }

    public int getLabel() {
        return label;
    }

    public double getRadius() {
        return radius;
    }

}
