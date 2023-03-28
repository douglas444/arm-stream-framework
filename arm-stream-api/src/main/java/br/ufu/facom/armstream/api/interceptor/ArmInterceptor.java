package br.ufu.facom.armstream.api.interceptor;

@FunctionalInterface
public interface ArmInterceptor {
    ArmInterceptionResult intercept(ArmInterceptionContext context);
}
