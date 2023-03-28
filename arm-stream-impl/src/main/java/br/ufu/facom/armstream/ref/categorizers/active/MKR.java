package br.ufu.facom.armstream.ref.categorizers.active;

import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MKR extends MajorityOf implements Cloneable {

    private int k = 1;
    private long seed = 0;

    @Override
    protected List<ArmDataInstance> selectDataInstancesToBeLabeled(final ArmInterceptionContext context) {

        final Random random = new Random(this.seed);

        final List<ArmDataInstance> unlabeledDataInstances = context
                .getClusterDataInstances()
                .stream()
                .filter(dataInstance -> !dataInstance.isTrueLabelAvailable())
                .collect(Collectors.toList());

        final List<ArmDataInstance> selected = new ArrayList<>();

        if (unlabeledDataInstances.size() < this.k) {
            selected.addAll(unlabeledDataInstances);
        } else {
            for (int i = 0; i < this.k; ++i) {
                selected.add(unlabeledDataInstances.remove(random.nextInt(unlabeledDataInstances.size())));
            }
        }

        return selected;
    }

    @Override
    public MKR clone() {
        try {
            return (MKR) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public MKR withK(final int k) {
        final MKR clone = this.clone();
        clone.k = k;
        return clone;
    }

    public MKR withSeed(final long seed) {
        final MKR clone = this.clone();
        clone.seed = seed;
        return clone;
    }

    //Getters

    public int getK() {
        return k;
    }

    public long getSeed() {
        return seed;
    }
}
