package br.ufu.facom.armstream.api;

import br.ufu.facom.armstream.api.data.ArmClusterCategory;
import br.ufu.facom.armstream.api.data.InterceptionContext;

public interface MetaCategorizer extends Parameterizable {
    ArmClusterCategory categorize(final InterceptionContext context);
}
