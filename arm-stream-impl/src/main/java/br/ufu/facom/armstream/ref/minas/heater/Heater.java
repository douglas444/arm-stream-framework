package br.ufu.facom.armstream.ref.minas.heater;

import br.ufu.facom.armstream.ref.minas.MicroCluster;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Heater {

    private final int k;
    private final Random random;
    private final int initialBufferSize;
    private final HashMap<Integer, AgglomerativeBuffer> bufferByLabel;

    public Heater(final int initialBufferSize, final int k, final Random random) {

        this.initialBufferSize = initialBufferSize;
        this.k = k;
        this.random = random;
        this.bufferByLabel = new HashMap<>();

    }

    public void process(final Sample sample) {

        final int label = sample.getY();

        if (!this.bufferByLabel.containsKey(label)) {

            final AgglomerativeBuffer buffer = new AgglomerativeBuffer(label, this.initialBufferSize, this.k, this.random);
            this.bufferByLabel.putIfAbsent(label, buffer);
        }

        final AgglomerativeBuffer ab = this.bufferByLabel.get(label);
        ab.add(sample);

    }

    public List<MicroCluster> getResult() {

        final List<MicroCluster> microClusters = new ArrayList<>();
        this.bufferByLabel.forEach((label, ab) -> microClusters.addAll(ab.getBuffer()));
        return microClusters;

    }

}
