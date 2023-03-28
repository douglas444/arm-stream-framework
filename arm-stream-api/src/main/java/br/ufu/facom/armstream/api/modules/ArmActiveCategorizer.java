package br.ufu.facom.armstream.api.modules;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;

@FunctionalInterface
public interface ArmActiveCategorizer {
    ArmInterceptionResult categorize(ArmInterceptionContext context);
}
