package br.ufu.facom.armstream.ref.categorizers.active;

import br.ufu.facom.armstream.api.datastructure.ArmClusterCategory;
import br.ufu.facom.armstream.api.datastructure.ArmDataInstance;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionContext;
import br.ufu.facom.armstream.api.interceptor.ArmInterceptionResult;
import br.ufu.facom.armstream.api.modules.ArmActiveCategorizer;

import java.util.ArrayList;
import java.util.List;

public class Dummy implements ArmActiveCategorizer {

    @Override
    public ArmInterceptionResult categorize(final ArmInterceptionContext context) {
        return new ArmInterceptionResult() {
            @Override
            public ArmClusterCategory getPrediction() {
                return context.getPredictedCategory();
            }

            @Override
            public List<ArmDataInstance> getLabeledDataInstances() {
                return new ArrayList<>();
            }
        };
    }
}
