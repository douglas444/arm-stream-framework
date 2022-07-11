package br.ufu.facom.armstream.api;

public interface BaseClassifier extends Parameterizable {
    boolean execute(final ArmInterceptor interceptor);
    void stop();
}
