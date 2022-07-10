package br.ufu.facom.armstream.api;

public interface ArmActiveCategorizer {
    ArmInterceptionResult categorize(final ArmInterceptionContext context);
}
