package br.ufu.facom.armstream.exp.workspace;

import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;
import br.ufu.facom.armstream.api.modules.ArmMetaCategorizer;
import br.ufu.facom.armstream.ref.categorizers.active.MKCN;
import br.ufu.facom.armstream.ref.categorizers.active.MKR;
import br.ufu.facom.armstream.ref.categorizers.meta.*;
import br.ufu.facom.armstream.ref.cdscal.ArmCdscal;
import br.ufu.facom.armstream.ref.echo.armstream.ArmEcho;
import br.ufu.facom.armstream.ref.minas.armstream.ArmMinas;

import java.io.FileNotFoundException;

import static br.ufu.facom.armstream.exp.util.FileUtil.getPathForResource;

public class ExperimentWorkspace {

    public final ArmMetaCategorizer[] metaCategorizers;
    public final ArmActiveCategorizer[] activeCategorizers;
    public final GroupedErrorEstimateCategorizer[] groupedErrorCategorizers;

    public final ArmMinas minasMOA3;
    public final ArmMinas minasSynEDC;
    public final ArmMinas minasKDD99;
    public final ArmMinas minasCovtype;

    public final ArmEcho echoMOA3;
    public final ArmEcho echoSynEDC;
    public final ArmEcho echoKDD99;
    public final ArmEcho echoCovtype;

    public final ArmCdscal cdscalMOA3;
    public final ArmCdscal cdscalSynEDC;
    public final ArmCdscal cdscalCovtype;
    public final ArmCdscal cdscalKDD99;

    public ExperimentWorkspace() throws FileNotFoundException {

        this.metaCategorizers = new ArmMetaCategorizer[4];
        this.metaCategorizers[0] = new NNCR().withDimensionality(1).withThresholdFactor(0.8);
        this.metaCategorizers[1] = new NNAR().withDimensionality(1).withThresholdFactor(0.8);
        this.metaCategorizers[2] = new NDNCR().withDimensionality(1).withThresholdFactor(0.9);
        this.metaCategorizers[3] = new NDNAR().withDimensionality(1).withThresholdFactor(0.9);

        this.activeCategorizers = new ArmActiveCategorizer[4];
        this.activeCategorizers[0] = new MKCN().withK(1);
        this.activeCategorizers[1] = new MKCN().withK(3);
        this.activeCategorizers[2] = new MKR().withK(1).withSeed(0);
        this.activeCategorizers[3] = new MKR().withK(3).withSeed(0);

        this.groupedErrorCategorizers = new GroupedErrorEstimateCategorizer[4];
        this.groupedErrorCategorizers[0] = (GroupedErrorEstimateCategorizer) this.metaCategorizers[0];
        this.groupedErrorCategorizers[1] = (GroupedErrorEstimateCategorizer) this.metaCategorizers[1];
        this.groupedErrorCategorizers[2] = (GroupedErrorEstimateCategorizer) this.metaCategorizers[2];
        this.groupedErrorCategorizers[3] = (GroupedErrorEstimateCategorizer) this.metaCategorizers[3];

        final ArmMinas minas = new ArmMinas()
                .withTemporaryMemoryMaxSize(2000)
                .withMinimumClusterSize(20)
                .withHeaterNumberOfClustersPerLabel(100)
                .withNoveltyDetectionNumberOfClusters(100)
                .withIncrementallyUpdateDecisionModel(false)
                .withSampleLifespan(4000)
                .withRandomGeneratorSeed(0)
                .withWindowSize(4000)
                .withMicroClusterLifespan(4000);

        this.minasMOA3 = minas
                .withDatasetFilePaths(getPathForResource("MOA3.csv"))
                .withHeaterCapacity(10000)
                .withHeaterInitialBufferSize(1000);

        this.minasSynEDC = minas
                .withDatasetFilePaths(getPathForResource("SynEDC-1.csv"),
                        getPathForResource("SynEDC-2.csv"),
                        getPathForResource("SynEDC-3.csv"))
                .withHeaterCapacity(40000)
                .withHeaterInitialBufferSize(5000);

        this.minasKDD99 = minas
                .withDatasetFilePaths(getPathForResource("kdd99.csv"))
                .withHeaterCapacity(48993)
                .withHeaterInitialBufferSize(6000);

        this.minasCovtype = minas
                .withDatasetFilePaths(getPathForResource("covtype-1.csv"),
                        getPathForResource("covtype-2.csv"),
                        getPathForResource("covtype-3.csv"))
                .withHeaterCapacity(47045)
                .withHeaterInitialBufferSize(6000);

        final ArmEcho echo = new ArmEcho()
                .withQ(400)
                .withK(25)
                .withCentroidPercentage(10)
                .withMciKMeansMaxIterations(10)
                .withConditionalModeMaxIterations(10)
                .withGamma(0.5)
                .withSensitivity(0.001)
                .withConfidenceThreshold(0.7)
                .withActiveLearningThreshold(0.4)
                .withFilteredOutlierBufferMaxSize(2000)
                .withEnsembleSize(5)
                .withRandomGeneratorSeed(0)
                .withKeepNoveltyDecisionModel(true)
                .withMultiClassNoveltyDetection(true);

        this.echoMOA3 = echo
                .withDatasetFilePaths(getPathForResource("MOA3.csv"))
                .withConfidenceWindowMaxSize(2000)
                .withChunkSize(2000);

        this.echoSynEDC = echo
                .withDatasetFilePaths(getPathForResource("SynEDC-1.csv"),
                        getPathForResource("SynEDC-2.csv"),
                        getPathForResource("SynEDC-3.csv"))
                .withConfidenceWindowMaxSize(8000)
                .withChunkSize(8000);

        this.echoKDD99 = echo
                .withDatasetFilePaths(getPathForResource("kdd99.csv"))
                .withConfidenceWindowMaxSize(9799)
                .withChunkSize(9799);

        this.echoCovtype = echo
                .withDatasetFilePaths(getPathForResource("covtype-1.csv"),
                        getPathForResource("covtype-2.csv"),
                        getPathForResource("covtype-3.csv"))
                .withConfidenceWindowMaxSize(9409)
                .withChunkSize(9409);

        final ArmCdscal armCdscal = new ArmCdscal()
                .withRemoteBaseClassifierUrl("http://localhost:5000/start");

        this.cdscalMOA3 = armCdscal
                .withDatasetFileName("MOA3.csv")
                .withModule("main_final_draft4")
                .withNormalize(1)
                .withBufferSize(2000);

        this.cdscalSynEDC = armCdscal
                .withDatasetFileName("SynEDC-1.csv;SynEDC-2.csv;SynEDC-3.csv")
                .withModule("main_final_draft4")
                .withNormalize(1)
                .withBufferSize(1000);

        this.cdscalKDD99 = armCdscal
                .withDatasetFileName("kdd99.csv")
                .withModule("main_final_draft4")
                .withNormalize(1)
                .withBufferSize(2000);

        this.cdscalCovtype = armCdscal
                .withDatasetFileName("covtype-1.csv;covtype-2.csv;covtype-3.csv")
                .withModule("main_final_draft")
                .withNormalize(1)
                .withBufferSize(2000);

    }

}
