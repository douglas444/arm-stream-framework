package br.ufu.facom.armstream.http;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class InterceptorService {

    private static InterceptorService instance;
    private ArmInterceptor interceptor;
    private Consumer<HashMap<String, String>> peeker;

    private CountDownLatch latch = new CountDownLatch(1);

    private InterceptorService() {
    }

    public static InterceptorService getInstance() {
        if (instance == null) {
            instance = new InterceptorService();
        }
        return instance;
    }

    public ArmInterceptionResult intercept(ArmInterceptionContext context) {
        if (this.interceptor == null) {
            throw new IllegalStateException("Interceptor is null");
        }
        return this.interceptor.intercept(context);
    }

    public void peek(LinkedHashMap<String, String> properties) {
        this.peeker.accept(properties);
    }

    public void finish() {
        this.latch.countDown();
        this.latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setInterceptor(ArmInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void setPeeker(Consumer<HashMap<String, String>> peeker) {
        this.peeker = peeker;
    }

}
