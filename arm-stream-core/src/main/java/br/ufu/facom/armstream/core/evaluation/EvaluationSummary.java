package br.ufu.facom.armstream.core.evaluation;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;

import java.util.Objects;

public class EvaluationSummary {

    private final CategorizationConfusionMatrix baseCategorizerConfusionMatrix;

    private final int numberOfMetaCategorizers;
    private final CategorizationConfusionMatrix[] metaCategorizerConfusionMatrix;
    private final QueryConfusionMatrix[] queryConfusionMatrixKnown;
    private final QueryConfusionMatrix[] queryConfusionMatrixNovelty;

    private final int numberOfActiveCategorizers;
    private final CategorizationConfusionMatrix[][] activeCategorizerConfusionMatrix;
    private final RecoveryConfusionMatrix[][] recoveryConfusionMatrix;

    public EvaluationSummary(final int numberOfMetaCategorizers, final int numberOfActiveCategorizers) {

        this.baseCategorizerConfusionMatrix = new CategorizationConfusionMatrix();

        this.numberOfMetaCategorizers = numberOfMetaCategorizers;
        this.metaCategorizerConfusionMatrix = new CategorizationConfusionMatrix[numberOfMetaCategorizers];
        this.queryConfusionMatrixKnown = new QueryConfusionMatrix[numberOfMetaCategorizers];
        this.queryConfusionMatrixNovelty = new QueryConfusionMatrix[numberOfMetaCategorizers];

        this.numberOfActiveCategorizers = numberOfActiveCategorizers;
        this.activeCategorizerConfusionMatrix = new CategorizationConfusionMatrix[numberOfMetaCategorizers][numberOfActiveCategorizers];
        this.recoveryConfusionMatrix = new RecoveryConfusionMatrix[numberOfMetaCategorizers][numberOfActiveCategorizers];

        for (int i = 0; i < numberOfMetaCategorizers; ++i) {

            this.metaCategorizerConfusionMatrix[i] = new CategorizationConfusionMatrix();
            this.queryConfusionMatrixKnown[i] = new QueryConfusionMatrix();
            this.queryConfusionMatrixNovelty[i] = new QueryConfusionMatrix();

            for (int j = 0; j < numberOfActiveCategorizers; ++j) {
                this.activeCategorizerConfusionMatrix[i][j] = new CategorizationConfusionMatrix();
                this.recoveryConfusionMatrix[i][j] = new RecoveryConfusionMatrix(this.baseCategorizerConfusionMatrix);
            }
        }
    }

    public void update(final ArmClusterCategory trueCategory,
                       final ArmClusterCategory basePrediction,
                       final ArmClusterCategory[] metaPredictions,
                       final ArmClusterCategory[][] activePredictions) {

        Objects.requireNonNull(trueCategory);
        Objects.requireNonNull(basePrediction);

        this.baseCategorizerConfusionMatrix.increment(trueCategory, basePrediction);

        for (int i = 0; i < this.numberOfMetaCategorizers; ++i) {

            Objects.requireNonNull(metaPredictions[i]);

            this.metaCategorizerConfusionMatrix[i].increment(trueCategory, metaPredictions[i]);

            if (trueCategory == ArmClusterCategory.NOVELTY) {
                this.queryConfusionMatrixNovelty[i].increment(trueCategory, basePrediction, metaPredictions[i]);
            } else {
                this.queryConfusionMatrixKnown[i].increment(trueCategory, basePrediction, metaPredictions[i]);
            }

            for (int j = 0; j < this.numberOfActiveCategorizers; ++j) {

                if (basePrediction != metaPredictions[i]) {
                    Objects.requireNonNull(activePredictions[i][j]);
                    this.activeCategorizerConfusionMatrix[i][j].increment(trueCategory, activePredictions[i][j]);
                }

                this.recoveryConfusionMatrix[i][j].increment(
                        trueCategory,
                        basePrediction,
                        metaPredictions[i],
                        activePredictions[i][j]);
            }
        }
    }

    //Getters

    public int getNumberOfMetaCategorizers() {
        return numberOfMetaCategorizers;
    }

    public int getNumberOfActiveCategorizers() {
        return numberOfActiveCategorizers;
    }

    public CategorizationConfusionMatrix getBaseCategorizerConfusionMatrix() {
        return baseCategorizerConfusionMatrix;
    }

    public CategorizationConfusionMatrix[] getMetaCategorizerConfusionMatrix() {
        return metaCategorizerConfusionMatrix;
    }

    public QueryConfusionMatrix[] getQueryConfusionMatrixKnown() {
        return queryConfusionMatrixKnown;
    }

    public QueryConfusionMatrix[] getQueryConfusionMatrixNovelty() {
        return queryConfusionMatrixNovelty;
    }

    public CategorizationConfusionMatrix[][] getActiveCategorizerConfusionMatrix() {
        return activeCategorizerConfusionMatrix;
    }

    public RecoveryConfusionMatrix[][] getRecoveryConfusionMatrix() {
        return recoveryConfusionMatrix;
    }

}
