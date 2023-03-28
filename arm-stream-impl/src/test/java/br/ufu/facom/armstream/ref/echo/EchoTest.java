package br.ufu.facom.armstream.ref.echo;

import br.ufu.facom.armstream.ref.util.file.DataStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest {

    private static final String DATASET_RESOURCE_FILEPATH = "MOA3.csv";
    private static final int Q = 400;
    private static final int K = 50;
    private static final int CENTROIDS_PERCENTAGE = 10;
    private static final int MCIKMEANS_MAX_ITERATIONS = 10;
    private static final int CONDITIONAL_MODE_MAX_ITERATIONS = 10;
    private static final double GAMMA = 0.5;
    private static final double SENSITIVITY = 0.001;
    private static final double CONFIDENCE_THRESHOLD = 0.6;
    private static final double ACTIVE_LEARNING_THRESHOLD = 0.4;
    private static final int FILTERED_OUTLIER_BUFFER_MAX_SIZE = 2000;
    private static final int CONFIDENCE_WINDOW_MAX_SIZE = 1000;
    private static final int ENSEMBLE_SIZE = 5;
    private static final int RANDOM_GENERATOR_SEED = 0;
    private static final int CHUNK_SIZE = 2000;
    private static final boolean KEEP_NOVELTY_DECISION_MODEL = true;
    private static final boolean MULTI_CLASS_NOVELTY_DETECTION = true;

    @Test
    public void execute() throws IOException, URISyntaxException {

        final Echo echo = new Echo(
                Q,
                K,
                CENTROIDS_PERCENTAGE,
                MCIKMEANS_MAX_ITERATIONS,
                CONDITIONAL_MODE_MAX_ITERATIONS,
                GAMMA,
                SENSITIVITY,
                CONFIDENCE_THRESHOLD,
                ACTIVE_LEARNING_THRESHOLD,
                FILTERED_OUTLIER_BUFFER_MAX_SIZE,
                CONFIDENCE_WINDOW_MAX_SIZE,
                ENSEMBLE_SIZE,
                RANDOM_GENERATOR_SEED,
                CHUNK_SIZE,
                KEEP_NOVELTY_DECISION_MODEL,
                MULTI_CLASS_NOVELTY_DETECTION,
                null);

        final URL url = getClass().getClassLoader().getResource(DATASET_RESOURCE_FILEPATH);
        if (url == null) {
            throw new MissingResourceException("File not found", EchoTest.class.getName(), DATASET_RESOURCE_FILEPATH);
        }

        URI uri = new URI(url.toString());//Required to prevent the encoding of special characters
        DataStream.from(uri.getPath()).forEach(echo::process);

        //Asserting UnkR
        double unkR = echo.getConfusionMatrix().unknownRate();
        unkR = (double) Math.round(unkR * 10000) / 10000;
        assertEquals(0.1308, unkR);

        //Asserting CER
        double cer = echo.getConfusionMatrix().combinedError();
        cer = (double) Math.round(cer * 10000) / 10000;
        assertEquals(0.0000, cer);

        //Asserting number of novelties
        assertEquals(108, echo.getNoveltyCount());

    }

}