package br.ufu.facom.armstream.ref.minas;

import java.util.function.Consumer;

public class Classification {

    private final MicroCluster closestMicroCluster;
    private final boolean explained;

    public Classification(final MicroCluster closestMicroCluster, final boolean explained) {

        if (explained && closestMicroCluster == null) {
            throw new IllegalArgumentException();
        }

        this.closestMicroCluster = closestMicroCluster;
        this.explained = explained;
    }

    public void ifExplainedOrElse(final Consumer<MicroCluster> consumer, final Runnable runnable) {

        if (this.explained) {
            consumer.accept(this.closestMicroCluster);
        } else {
            runnable.run();
        }

    }

    public boolean isExplained() {
        return explained;
    }

    public MicroCluster getClosestMicroCluster() {
        return closestMicroCluster;
    }
}