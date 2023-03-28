package br.ufu.facom.armstream.ref.categorizers.active;

import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.util.*;
import java.util.stream.Collectors;

public class MKCN extends MajorityOf implements Cloneable {

    private int k = 1;

    @Override
    protected List<ArmDataInstance> selectDataInstancesToBeLabeled(final ArmInterceptionContext context) {

        final double[] centroid = context.getClusterSummary().getCentroidAttributes();

        final List<ArmDataInstance> sortedUnlabeledDataInstances = context
                .getClusterDataInstances()
                .stream()
                .filter(dataInstance -> !dataInstance.isTrueLabelAvailable())
                .sorted(Comparator.comparing(dataInstance -> Sample.distance(centroid, dataInstance.getAttributes())))
                .collect(Collectors.toList());

        final List<ArmDataInstance> selected = new ArrayList<>();

        if (!sortedUnlabeledDataInstances.isEmpty()) {
            if (sortedUnlabeledDataInstances.size() > this.k) {
                selected.addAll(sortedUnlabeledDataInstances.subList(0, this.k));
            } else {
                selected.addAll(sortedUnlabeledDataInstances);
            }
        }

        return selected;
    }

    @Override
    public MKCN clone() {
        try {
            return (MKCN) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public MKCN withK(final int k) {
        final MKCN clone = this.clone();
        clone.k = k;
        return clone;
    }

    //Getters

    public int getK() {
        return k;
    }
}
