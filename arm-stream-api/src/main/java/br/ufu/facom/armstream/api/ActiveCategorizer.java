package br.ufu.facom.armstream.api;

import br.ufu.facom.armstream.api.data.InterceptionContext;
import br.ufu.facom.armstream.api.data.InterceptionResult;

public interface ActiveCategorizer extends Parameterizable {
    InterceptionResult categorize(final InterceptionContext context);
}
