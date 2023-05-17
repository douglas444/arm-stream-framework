package br.ufu.facom.armstream.ref.minas.armstream;

import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

public class ArmDataInstanceImpl implements ArmDataInstance {

    private final double[] attributes;
    private final int trueLabel;
    private final boolean trueLabelAvailable;

    public ArmDataInstanceImpl(final Sample sample) {
        this.attributes = sample.getX().clone();
        this.trueLabel = sample.getY();
        this.trueLabelAvailable = false;
    }

    @Override
    public double[] getAttributes() {
        return this.attributes;
    }

    @Override
    public int getTrueLabel() {
        return this.trueLabel;
    }

    @Override
    public boolean isTrueLabelAvailable() {
        return this.trueLabelAvailable;
    }
}
