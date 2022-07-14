package br.ufu.facom.armstream.api;

public interface ArmBaseClassifier extends Parameterizable {
    boolean execute(final ArmInterceptor interceptor);
    void stop();
}
