package br.ufu.facom.armstream.api.modules;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;

import java.util.HashMap;
import java.util.function.Consumer;

public interface ArmBaseClassifier {
    void run(ArmInterceptor interceptor, Consumer<HashMap<String, String>> peeker) throws Exception;
}
