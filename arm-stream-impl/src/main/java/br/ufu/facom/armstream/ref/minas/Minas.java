package br.ufu.facom.armstream.ref.minas;

import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;
import br.ufu.facom.armstream.ref.minas.armstream.ArmInterceptionContextImpl;
import br.ufu.facom.armstream.ref.minas.heater.Heater;
import br.ufu.facom.armstream.ref.util.algorithms.KMeans;
import br.ufu.facom.armstream.ref.util.algorithms.KMeansPlusPlus;
import br.ufu.facom.armstream.ref.util.algorithms.Silhouette;
import br.ufu.facom.armstream.ref.util.datastructures.Cluster;
import br.ufu.facom.armstream.ref.util.datastructures.DynamicConfusionMatrix;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.*;
import java.util.stream.Collectors;

public class Minas {

    private long timestamp;
    private int noveltyCount;
    private boolean warmed;
    private int heaterCapacity;

    private final DecisionModel decisionModel;
    private final List<Sample> temporaryMemory;
    private final DecisionModel sleepMemory;
    private final DynamicConfusionMatrix confusionMatrix;

    private final int temporaryMemoryMaxSize;
    private final int minimumClusterSize;
    private final int windowSize;
    private final int microClusterLifespan;
    private final int sampleLifespan;
    private final Random random;
    private final int noveltyDetectionNumberOfClusters;
    private final Heater heater;
    private final ArmInterceptor armInterceptor;

    public Minas(final int temporaryMemoryMaxSize,
                 final int minimumClusterSize,
                 final int windowSize,
                 final int microClusterLifespan,
                 final int sampleLifespan,
                 final int noveltyDetectionNumberOfClusters,
                 final boolean incrementallyUpdateDecisionModel,
                 final int heaterCapacity,
                 final int heaterInitialBufferSize,
                 final int heaterNumberOfClustersPerLabel,
                 final long randomGeneratorSeed,
                 final ArmInterceptor interceptor) {

        this.timestamp = 1;
        this.noveltyCount = 0;
        this.warmed = false;
        this.temporaryMemory = new ArrayList<>();

        this.heaterCapacity = heaterCapacity;
        this.temporaryMemoryMaxSize = temporaryMemoryMaxSize;
        this.minimumClusterSize = minimumClusterSize;
        this.windowSize = windowSize;
        this.microClusterLifespan = microClusterLifespan;
        this.sampleLifespan = sampleLifespan;
        this.noveltyDetectionNumberOfClusters = noveltyDetectionNumberOfClusters;
        this.random = new Random(randomGeneratorSeed);

        this.armInterceptor = interceptor;

        this.heater = new Heater(heaterInitialBufferSize, heaterNumberOfClustersPerLabel, this.random);
        this.decisionModel = new DecisionModel(incrementallyUpdateDecisionModel);
        this.sleepMemory = new DecisionModel(incrementallyUpdateDecisionModel);
        this.confusionMatrix = new DynamicConfusionMatrix();

    }

    private void warmUp(final Sample sample) {

        assert !warmed;

        if (this.confusionMatrix.isLabelUnknown(sample.getY())) {
            this.confusionMatrix.addKnownLabel(sample.getY());
        }

        this.heater.process(sample);

        if (this.heaterCapacity > 1) {
            this.heaterCapacity--;
        } else {

            this.warmed = true;

            final List<MicroCluster> microClusters = this.heater.getResult().stream()
                    .filter(microCluster -> microCluster.getN() >= 3)
                    .collect(Collectors.toCollection(ArrayList::new));

            microClusters.forEach(microCluster -> microCluster.setTimestamp(this.timestamp));
            this.decisionModel.merge(microClusters);
        }

    }

    private void noveltyDetection() {

        final List<Cluster> clusters = KMeansPlusPlus
                .execute(this.temporaryMemory, this.noveltyDetectionNumberOfClusters, this.random)
                .stream()
                .filter(cluster -> cluster.getSize() >= this.minimumClusterSize)
                .sorted(Comparator.comparing(cluster -> cluster.getMostRecentSample().getT()))
                .collect(Collectors.toList());

        for (final Cluster cluster : clusters) {

            final List<Sample> decisionModelCentroids = this.decisionModel.getMicroClusters()
                    .stream()
                    .map(MicroCluster::calculateCentroid)
                    .collect(Collectors.toList());

            if (Silhouette.calculate(cluster, decisionModelCentroids) <= 0) {
                continue;
            }

            categorizeAndUpdateModel(cluster);
        }

    }

    private void categorizeAndUpdateModel(final Cluster cluster) {

        this.temporaryMemory.removeAll(cluster.getSamples());

        final MicroCluster microCluster = new MicroCluster(cluster.getSamples(), cluster.getMostRecentSample().getT());

        final List<ArmDataInstance> labeledDataInstances = new ArrayList<>();
        final Optional<MicroCluster> closest;
        final boolean awakened;

        final Classification classification = this.decisionModel.categorize(microCluster);

        if (classification.isExplained()) {

            closest = Optional.of(classification.getClosestMicroCluster());
            awakened = false;

        } else {

            final Classification sleepMemoryClassification = this.sleepMemory.categorize(microCluster);
            if (sleepMemoryClassification.isExplained()) {

                closest = Optional.of(sleepMemoryClassification.getClosestMicroCluster());
                awakened = true;

            } else {

                closest = Optional.empty();
                awakened = false;
            }

        }

        final MicroClusterCategory predictedCategory = closest.isPresent() ?
                closest.get().getMicroClusterCategory() : MicroClusterCategory.NOVELTY;

        if (this.armInterceptor != null) {

            final ArmInterceptionContextImpl context = new ArmInterceptionContextImpl(
                    microCluster,
                    cluster.getSamples(),
                    this.decisionModel.getMicroClusters(),
                    this.sleepMemory.getMicroClusters(),
                    predictedCategory);

            labeledDataInstances.addAll(this.armInterceptor.intercept(context).getLabeledDataInstances());
        }


        if (labeledDataInstances.isEmpty()) {

            final Integer label;

            if (closest.isPresent()) {

                label = closest.get().getLabel();

                if (awakened) {
                    this.sleepMemory.remove(closest.get());
                    this.decisionModel.merge(closest.get());
                }
            } else {
                label = this.noveltyCount++;
            }

            this.updateModelWithUnlabeledCluster(microCluster, predictedCategory, label, cluster.getSamples());

        } else {
            this.updateModelWithPartiallyLabeledCluster(cluster, labeledDataInstances);
        }

    }

    private void updateModelWithUnlabeledCluster(final MicroCluster microCluster,
                                                 final MicroClusterCategory category,
                                                 final Integer label,
                                                 final List<Sample> samples) {
        microCluster.setMicroClusterCategory(category);
        microCluster.setLabel(label);
        this.decisionModel.merge(microCluster);

        for (final Sample sample : samples) {
            this.confusionMatrix.updatedDelayed(sample.getY(), label, category == MicroClusterCategory.NOVELTY);
        }
    }


    private void updateModelWithPartiallyLabeledCluster(final Cluster cluster,
                                                        final List<ArmDataInstance> labeledDataInstances) {

        final List<Cluster> subClusters = generateOneClusterPerLabeledDataInstance(cluster.getSamples(), labeledDataInstances);

        for (final Cluster subCluster : subClusters) {

            final MicroCluster subMicroCluster = new MicroCluster(
                    subCluster.getSamples(),
                    subCluster.getMostRecentSample().getT());

            updateModelWithUnlabeledCluster(
                    subMicroCluster,
                    MicroClusterCategory.KNOWN,
                    subCluster.getLabel(),
                    subCluster.getSamples());
        }

    }

    private List<Cluster> generateOneClusterPerLabeledDataInstance(final List<Sample> samples,
                                                                   final List<ArmDataInstance> labeledDataInstances) {

        if (labeledDataInstances.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final List<Sample> centroids = labeledDataInstances
                .stream()
                .map(dataInstance -> new Sample(dataInstance.getAttributes(), dataInstance.getTrueLabel()))
                .collect(Collectors.toList());

        final HashMap<Sample, List<Sample>> samplesByClosestLabeledDataInstance = KMeans.executeSingleIteration(
                samples, centroids);

        return samplesByClosestLabeledDataInstance
                .entrySet()
                .stream()
                .map(entry -> {
                    final Cluster cluster = new Cluster(entry.getValue());
                    cluster.setLabel(entry.getKey().getY());
                    return cluster;
                })
                .collect(Collectors.toList());

    }

    public Classification process(final Sample sample) {

        if (!this.warmed) {
            this.warmUp(sample);
            return new Classification(null, false);
        }

        sample.setT(this.timestamp);

        final Classification classification = this.decisionModel.categorize(sample);

        classification.ifExplainedOrElse((closestMicroCluster) -> {

            this.confusionMatrix.addPrediction(
                    sample.getY(),
                    closestMicroCluster.getLabel(),
                    closestMicroCluster.getMicroClusterCategory() == MicroClusterCategory.NOVELTY);

        }, () -> {
            this.temporaryMemory.add(sample);
            this.confusionMatrix.addUnknown(sample.getY());
            if (this.temporaryMemory.size() >= this.temporaryMemoryMaxSize) {
                this.noveltyDetection();
            }
        });

        if (this.timestamp % this.windowSize == 0) {
            final List<MicroCluster> inactiveMicroClusters = this.decisionModel
                    .extractInactiveMicroClusters(this.timestamp, microClusterLifespan);
            this.sleepMemory.merge(inactiveMicroClusters);
            this.temporaryMemory.removeIf(p -> p.getT() < this.timestamp - this.sampleLifespan);
        }

        ++this.timestamp;

        return classification;

    }

    public long getTimestamp() {
        return timestamp - 1;
    }

    public DynamicConfusionMatrix getConfusionMatrix() {
        return confusionMatrix;
    }

    public int getNoveltyCount() {
        return noveltyCount;
    }

}