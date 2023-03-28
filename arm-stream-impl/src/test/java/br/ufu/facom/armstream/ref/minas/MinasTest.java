package br.ufu.facom.armstream.ref.minas;

import br.ufu.facom.armstream.ref.util.file.DataStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinasTest {

    private static final String DATASET_FILEPATH = "MOA3.csv";
    private static final int TEMPORARY_MEMORY_MAX_SIZE = 2000;
    private static final int MINIMUM_CLUSTER_SIZE = 20;
    private static final int WINDOW_SIZE = 4000;
    private static final int MICRO_CLUSTER_LIFESPAN = 4000;
    private static final int SAMPLE_LIFESPAN = 4000;
    private static final int HEATER_CAPACITY = 10000;
    private static final boolean INCREMENTALLY_UPDATE_DECISION_MODEL = false;
    private static final int HEATER_INITIAL_BUFFER_SIZE = 1000;
    private static final int HEATER_NUMBER_OF_CLUSTERS_PER_LABEL = 100;
    private static final int NOVELTY_DETECTION_NUMBER_OF_CLUSTERS = 100;
    private static final long RANDOM_GENERATOR_SEED = 0;

    @Test
    public void execute() throws IOException, URISyntaxException {

        final Minas minas = new Minas(
                TEMPORARY_MEMORY_MAX_SIZE,
                MINIMUM_CLUSTER_SIZE,
                WINDOW_SIZE,
                MICRO_CLUSTER_LIFESPAN,
                SAMPLE_LIFESPAN,
                NOVELTY_DETECTION_NUMBER_OF_CLUSTERS,
                INCREMENTALLY_UPDATE_DECISION_MODEL,
                HEATER_CAPACITY,
                HEATER_INITIAL_BUFFER_SIZE,
                HEATER_NUMBER_OF_CLUSTERS_PER_LABEL,
                RANDOM_GENERATOR_SEED,
                null);

        final URL url = getClass().getClassLoader().getResource(DATASET_FILEPATH);
        if (url == null) {
            throw new MissingResourceException("File not found", MinasTest.class.getName(), DATASET_FILEPATH);
        }

        URI uri = new URI(url.toString());//Required to prevent the encoding of special characters
        DataStream.from(uri.getPath()).forEach(minas::process);

        //Asserting UnkR
        double unkR = minas.getConfusionMatrix().unknownRate();
        unkR = (double) Math.round(unkR * 10000) / 10000;
        assertEquals(0.1106, unkR);

        //Asserting CER
        double cer = minas.getConfusionMatrix().combinedError();
        cer = (double) Math.round(cer * 10000) / 10000;
        assertEquals(0.0, cer);

        //Asserting number of novelties
        assertEquals(157, minas.getNoveltyCount());

    }

}