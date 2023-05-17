package br.ufu.facom.armstream.ref.minas;

import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class DecisionModel {

    private final boolean incrementallyUpdate;
    private final List<MicroCluster> microClusters;

    DecisionModel(final boolean incrementallyUpdate) {

        this.incrementallyUpdate = incrementallyUpdate;
        this.microClusters = new ArrayList<>();
    }

    Classification categorize(final Sample sample) {

        if (microClusters.isEmpty()) {
            return new Classification(null, false);
        }

        final MicroCluster closestMicroCluster = MicroCluster
                .calculateClosestMicroCluster(sample, microClusters);

        final double distance = sample.distance(closestMicroCluster.calculateCentroid());

        if (distance <= closestMicroCluster.calculateStandardDeviation() * 2) {
            closestMicroCluster.setTimestamp(sample.getT());
            if (this.incrementallyUpdate) {
                closestMicroCluster.update(sample);
            }
            return new Classification(closestMicroCluster, true);
        }

        return new Classification(closestMicroCluster, false);

    }

    Classification categorize(final MicroCluster microCluster) {

        if (this.microClusters.isEmpty()) {
            return new Classification(null, false);
        }

        final MicroCluster closestMicroCluster = microCluster.calculateClosestMicroCluster(this.microClusters);
        final double distance = microCluster.distance(closestMicroCluster);

        if (distance <= closestMicroCluster.calculateStandardDeviation()
                + microCluster.calculateStandardDeviation()) {
            return new Classification(closestMicroCluster, true);
        }

        return new Classification(closestMicroCluster, false);

    }

    void merge(final MicroCluster microCluster) {
        this.microClusters.add(microCluster);
    }

    void merge(final List<MicroCluster> microClusters) {
        this.microClusters.addAll(microClusters);
    }

    List<MicroCluster> extractInactiveMicroClusters(final long timestamp, final int lifespan) {

        final List<MicroCluster> inactiveMicroClusters = this.microClusters
                .stream()
                .filter(microCluster -> microCluster.getTimestamp() < timestamp - lifespan)
                .collect(Collectors.toList());

        this.microClusters.removeAll(inactiveMicroClusters);

        return inactiveMicroClusters;
    }

    List<MicroCluster> getMicroClusters() {
        return microClusters;
    }

    void remove(final MicroCluster microCluster) {
        this.microClusters.remove(microCluster);
    }
}
