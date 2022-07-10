package br.ufu.facom.armstream.api;

public interface ArmBaseClassifier {
    boolean execute(final ArmInterceptor interceptor);
    void stop();
}
