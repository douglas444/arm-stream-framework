package br.ufu.facom.armstream.api;

@FunctionalInterface
public interface ArmInterceptor {
    ArmInterceptionResult intercept(ArmInterceptionContext context);
}
