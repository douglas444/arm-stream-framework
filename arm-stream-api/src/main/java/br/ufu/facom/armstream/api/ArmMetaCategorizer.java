package br.ufu.facom.armstream.api;

public interface ArmMetaCategorizer {

    ArmClusterCategory categorize(final ArmInterceptionContext context);

}
