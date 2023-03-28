package br.ufu.facom.armstream.ref.minas.armstream;

import br.ufu.facom.armstream.ref.minas.Minas;
import br.ufu.facom.armstream.ref.util.file.DataStream;
import br.ufu.facom.armstream.api.modules.ArmBaseClassifier;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class ArmMinas implements ArmBaseClassifier, Cloneable {

    private String[] datasetFilePaths;
    private int temporaryMemoryMaxSize = 2000;
    private int minimumClusterSize = 20;
    private int windowSize = 4000;
    private int microClusterLifespan = 4000;
    private int sampleLifespan = 4000;
    private int noveltyDetectionNumberOfClusters = 100;
    private boolean incrementallyUpdateDecisionModel = false;
    private int heaterCapacity = 10000;
    private int heaterInitialBufferSize = 1000;
    private int heaterNumberOfClustersPerLabel = 100;
    private long randomGeneratorSeed = 0;

    @Override
    public void run(final ArmInterceptor interceptor, final Consumer<HashMap<String, String>> peeker) 
            throws IOException {

        final Minas minas = new Minas(
                this.temporaryMemoryMaxSize,
                this.minimumClusterSize,
                this.windowSize,
                this.microClusterLifespan,
                this.sampleLifespan,
                this.noveltyDetectionNumberOfClusters,
                this.incrementallyUpdateDecisionModel,
                this.heaterCapacity,
                this.heaterInitialBufferSize,
                this.heaterNumberOfClustersPerLabel,
                this.randomGeneratorSeed,
                interceptor);

        DataStream.from(this.datasetFilePaths).forEach((sample) -> {

            minas.process(sample);

            final LinkedHashMap<String, String> properties = new LinkedHashMap<>();
            properties.put("timestamp", String.valueOf(minas.getTimestamp()));
            properties.put("cer", String.valueOf(minas.getConfusionMatrix().combinedError()));
            properties.put("datasetFileName", Arrays.stream(this.datasetFilePaths)
                    .map(File::new)
                    .map(File::getName)
                    .reduce((s1, s2) -> s1 + ";" + s2)
                    .orElse(""));
            properties.put("unkR", String.valueOf(minas.getConfusionMatrix().unknownRate()));
            properties.put("noveltyCount", String.valueOf(minas.getNoveltyCount()));

            peeker.accept(properties);
        });

    }

    @Override
    public ArmMinas clone() {
        try {
            return (ArmMinas) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public ArmMinas withDatasetFilePaths(final String... datasetFilePaths) {
        final ArmMinas clone = this.clone();
        clone.datasetFilePaths = datasetFilePaths;
        return clone;
    }

    public ArmMinas withTemporaryMemoryMaxSize(final int temporaryMemoryMaxSize) {
        final ArmMinas clone = this.clone();
        clone.temporaryMemoryMaxSize = temporaryMemoryMaxSize;
        return clone;
    }

    public ArmMinas withMinimumClusterSize(final int minimumClusterSize) {
        final ArmMinas clone = this.clone();
        clone.minimumClusterSize = minimumClusterSize;
        return clone;
    }

    public ArmMinas withWindowSize(final int windowSize) {
        final ArmMinas clone = this.clone();
        clone.windowSize = windowSize;
        return clone;
    }

    public ArmMinas withMicroClusterLifespan(final int microClusterLifespan) {
        final ArmMinas clone = this.clone();
        clone.microClusterLifespan = microClusterLifespan;
        return clone;
    }

    public ArmMinas withSampleLifespan(final int sampleLifespan) {
        final ArmMinas clone = this.clone();
        clone.sampleLifespan = sampleLifespan;
        return clone;
    }

    public ArmMinas withNoveltyDetectionNumberOfClusters(final int noveltyDetectionNumberOfClusters) {
        final ArmMinas clone = this.clone();
        clone.noveltyDetectionNumberOfClusters = noveltyDetectionNumberOfClusters;
        return clone;
    }

    public ArmMinas withIncrementallyUpdateDecisionModel(final boolean incrementallyUpdateDecisionModel) {
        final ArmMinas clone = this.clone();
        clone.incrementallyUpdateDecisionModel = incrementallyUpdateDecisionModel;
        return clone;
    }

    public ArmMinas withHeaterCapacity(final int heaterCapacity) {
        final ArmMinas clone = this.clone();
        clone.heaterCapacity = heaterCapacity;
        return clone;
    }

    public ArmMinas withHeaterInitialBufferSize(final int heaterInitialBufferSize) {
        final ArmMinas clone = this.clone();
        clone.heaterInitialBufferSize = heaterInitialBufferSize;
        return clone;
    }

    public ArmMinas withHeaterNumberOfClustersPerLabel(final int heaterNumberOfClustersPerLabel) {
        final ArmMinas clone = this.clone();
        clone.heaterNumberOfClustersPerLabel = heaterNumberOfClustersPerLabel;
        return clone;
    }

    public ArmMinas withRandomGeneratorSeed(final long randomGeneratorSeed) {
        final ArmMinas clone = this.clone();
        clone.randomGeneratorSeed = randomGeneratorSeed;
        return clone;
    }

    //Getters

    public String[] getDatasetFilePaths() {
        return datasetFilePaths;
    }

    public int getTemporaryMemoryMaxSize() {
        return temporaryMemoryMaxSize;
    }

    public int getMinimumClusterSize() {
        return minimumClusterSize;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getMicroClusterLifespan() {
        return microClusterLifespan;
    }

    public int getSampleLifespan() {
        return sampleLifespan;
    }

    public int getNoveltyDetectionNumberOfClusters() {
        return noveltyDetectionNumberOfClusters;
    }

    public boolean isIncrementallyUpdateDecisionModel() {
        return incrementallyUpdateDecisionModel;
    }

    public int getHeaterCapacity() {
        return heaterCapacity;
    }

    public int getHeaterInitialBufferSize() {
        return heaterInitialBufferSize;
    }

    public int getHeaterNumberOfClustersPerLabel() {
        return heaterNumberOfClustersPerLabel;
    }

    public long getRandomGeneratorSeed() {
        return randomGeneratorSeed;
    }
}
