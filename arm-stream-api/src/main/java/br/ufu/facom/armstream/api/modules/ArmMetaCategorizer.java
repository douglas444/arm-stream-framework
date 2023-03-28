package br.ufu.facom.armstream.api.modules;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;

@FunctionalInterface
public interface ArmMetaCategorizer {
    ArmClusterCategory categorize(ArmInterceptionContext context);
}
