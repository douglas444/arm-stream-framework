package br.ufu.facom.armstream.api;

public interface ArmMetaCategorizer extends Parameterizable {
    ArmClusterCategory categorize(final ArmInterceptionContext context);
}
