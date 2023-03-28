package br.ufu.facom.armstream.ref.echo.armstream;

import br.ufu.facom.armstream.ref.util.datastructures.Sample;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;

public class ArmDataInstanceImpl implements ArmDataInstance {

    private final double[] attributes;
    private final int trueLabel;
    private final boolean trueLabelAvailable;

    public ArmDataInstanceImpl(final Sample sample, final boolean trueLabelAvailable) {
        this.attributes = sample.getX().clone();
        this.trueLabel = sample.getY();
        this.trueLabelAvailable = trueLabelAvailable;
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
