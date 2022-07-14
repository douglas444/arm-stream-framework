package br.ufu.facom.armstream.api;

import br.ufu.facom.armstream.api.data.ArmInterceptionContext;
import br.ufu.facom.armstream.api.data.ArmInterceptionResult;

public interface ArmInterceptor {
    ArmInterceptionResult intercept(ArmInterceptionContext context);
}
