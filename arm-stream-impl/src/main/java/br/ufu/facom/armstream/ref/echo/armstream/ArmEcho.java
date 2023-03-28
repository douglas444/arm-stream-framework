package br.ufu.facom.armstream.ref.echo.armstream;

import br.ufu.facom.armstream.api.modules.ArmBaseClassifier;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;
import br.ufu.facom.armstream.ref.echo.Echo;
import br.ufu.facom.armstream.ref.util.file.DataStream;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class ArmEcho implements ArmBaseClassifier, Cloneable {

    private String[] datasetFilePaths;
    private int q = 400;
    private int k = 50;
    private double centroidPercentage = 10;
    private int mciKMeansMaxIterations = 10;
    private int conditionalModeMaxIterations = 10;
    private double gamma = 0.5;
    private double sensitivity = 0.001;
    private double confidenceThreshold = 0.6;
    private double activeLearningThreshold = 0.5;
    private int filteredOutlierBufferMaxSize = 2000;
    private int confidenceWindowMaxSize = 1000;
    private int ensembleSize = 5;
    private int randomGeneratorSeed = 0;
    private int chunkSize = 2000;
    private boolean keepNoveltyDecisionModel = true;
    private boolean multiClassNoveltyDetection = true;

    @Override
    public void run(final ArmInterceptor interceptor, final Consumer<HashMap<String, String>> peeker)
            throws IOException {

        final Echo echo = new Echo(
                this.q,
                this.k,
                this.centroidPercentage,
                this.mciKMeansMaxIterations,
                this.conditionalModeMaxIterations,
                this.gamma,
                this.sensitivity,
                this.confidenceThreshold,
                this.activeLearningThreshold,
                this.filteredOutlierBufferMaxSize,
                this.confidenceWindowMaxSize,
                this.ensembleSize,
                this.randomGeneratorSeed,
                this.chunkSize,
                this.keepNoveltyDecisionModel,
                this.multiClassNoveltyDetection,
                interceptor);

        DataStream.from(this.datasetFilePaths).forEach((sample) -> {

            echo.process(sample);

            final LinkedHashMap<String, String> properties = new LinkedHashMap<>();
            properties.put("timestamp", String.valueOf(echo.getTimestamp()));
            properties.put("labeled", String.valueOf(echo.getLabeledSamplesCount()));
            properties.put("datasetFileName", Arrays.stream(this.datasetFilePaths)
                    .map(File::new)
                    .map(File::getName)
                    .reduce((s1, s2) -> s1 + ";" + s2)
                    .orElse(""));
            properties.put("meanConf", String.valueOf(echo.getMeanConfidence()));
            properties.put("cd", String.valueOf(echo.getConceptDriftsCount()));
            properties.put("cer", String.valueOf(echo.getConfusionMatrix().combinedError()));
            properties.put("unkR", String.valueOf(echo.getConfusionMatrix().unknownRate()));
            properties.put("noveltyCount", String.valueOf(echo.getNoveltyCount()));

            peeker.accept(properties);
        });

    }

    @Override
    public ArmEcho clone() {
        try {
            return (ArmEcho) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public ArmEcho withDatasetFilePaths(final String... datasetFilePaths) {
        final ArmEcho clone = this.clone();
        clone.datasetFilePaths = datasetFilePaths;
        return clone;
    }

    public ArmEcho withQ(final int q) {
        final ArmEcho clone = this.clone();
        clone.q = q;
        return clone;
    }

    public ArmEcho withK(final int k) {
        final ArmEcho clone = this.clone();
        clone.k = k;
        return clone;
    }

    public ArmEcho withCentroidPercentage(final double centroidPercentage) {
        final ArmEcho clone = this.clone();
        clone.centroidPercentage = centroidPercentage;
        return clone;
    }

    public ArmEcho withMciKMeansMaxIterations(final int mciKMeansMaxIterations) {
        final ArmEcho clone = this.clone();
        clone.mciKMeansMaxIterations = mciKMeansMaxIterations;
        return clone;
    }

    public ArmEcho withConditionalModeMaxIterations(final int conditionalModeMaxIterations) {
        final ArmEcho clone = this.clone();
        clone.conditionalModeMaxIterations = conditionalModeMaxIterations;
        return clone;
    }

    public ArmEcho withGamma(final double gamma) {
        final ArmEcho clone = this.clone();
        clone.gamma = gamma;
        return clone;
    }

    public ArmEcho withSensitivity(final double sensitivity) {
        final ArmEcho clone = this.clone();
        clone.sensitivity = sensitivity;
        return clone;
    }

    public ArmEcho withConfidenceThreshold(final double confidenceThreshold) {
        final ArmEcho clone = this.clone();
        clone.confidenceThreshold = confidenceThreshold;
        return clone;
    }

    public ArmEcho withActiveLearningThreshold(final double activeLearningThreshold) {
        final ArmEcho clone = this.clone();
        clone.activeLearningThreshold = activeLearningThreshold;
        return clone;
    }

    public ArmEcho withFilteredOutlierBufferMaxSize(final int filteredOutlierBufferMaxSize) {
        final ArmEcho clone = this.clone();
        clone.filteredOutlierBufferMaxSize = filteredOutlierBufferMaxSize;
        return clone;
    }

    public ArmEcho withConfidenceWindowMaxSize(final int confidenceWindowMaxSize) {
        final ArmEcho clone = this.clone();
        clone.confidenceWindowMaxSize = confidenceWindowMaxSize;
        return clone;
    }

    public ArmEcho withEnsembleSize(final int ensembleSize) {
        final ArmEcho clone = this.clone();
        clone.ensembleSize = ensembleSize;
        return clone;
    }

    public ArmEcho withRandomGeneratorSeed(final int randomGeneratorSeed) {
        final ArmEcho clone = this.clone();
        clone.randomGeneratorSeed = randomGeneratorSeed;
        return clone;
    }

    public ArmEcho withChunkSize(final int chunkSize) {
        final ArmEcho clone = this.clone();
        clone.chunkSize = chunkSize;
        return clone;
    }

    public ArmEcho withKeepNoveltyDecisionModel(final boolean keepNoveltyDecisionModel) {
        final ArmEcho clone = this.clone();
        clone.keepNoveltyDecisionModel = keepNoveltyDecisionModel;
        return clone;
    }

    public ArmEcho withMultiClassNoveltyDetection(final boolean multiClassNoveltyDetection) {
        final ArmEcho clone = this.clone();
        clone.multiClassNoveltyDetection = multiClassNoveltyDetection;
        return clone;
    }

    //Getters

    public String[] getDatasetFilePaths() {
        return datasetFilePaths;
    }

    public int getQ() {
        return q;
    }

    public int getK() {
        return k;
    }

    public double getCentroidPercentage() {
        return centroidPercentage;
    }

    public int getMciKMeansMaxIterations() {
        return mciKMeansMaxIterations;
    }

    public int getConditionalModeMaxIterations() {
        return conditionalModeMaxIterations;
    }

    public double getGamma() {
        return gamma;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public double getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public double getActiveLearningThreshold() {
        return activeLearningThreshold;
    }

    public int getFilteredOutlierBufferMaxSize() {
        return filteredOutlierBufferMaxSize;
    }

    public int getConfidenceWindowMaxSize() {
        return confidenceWindowMaxSize;
    }

    public int getEnsembleSize() {
        return ensembleSize;
    }

    public int getRandomGeneratorSeed() {
        return randomGeneratorSeed;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public boolean isKeepNoveltyDecisionModel() {
        return keepNoveltyDecisionModel;
    }

    public boolean isMultiClassNoveltyDetection() {
        return multiClassNoveltyDetection;
    }
}