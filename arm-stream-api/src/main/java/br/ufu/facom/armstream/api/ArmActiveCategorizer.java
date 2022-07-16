package br.ufu.facom.armstream.api;

public interface ArmActiveCategorizer extends Parameterizable {
    ArmInterceptionResult categorize(final ArmInterceptionContext context);
}
