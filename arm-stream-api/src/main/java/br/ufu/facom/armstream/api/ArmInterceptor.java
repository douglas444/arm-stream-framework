package br.ufu.facom.armstream.api;

public interface ArmInterceptor {
    ArmInterceptionResult intercept(ArmInterceptionContext context);
}
