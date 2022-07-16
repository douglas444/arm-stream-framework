package br.ufu.facom.armstream.api;

public interface ArmBaseClassifier extends Parameterizable {
    void run(final ArmInterceptor interceptor) throws Exception;
    void kill() throws Exception;
}
