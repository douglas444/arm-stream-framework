package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.exp.experiments.ThresholdFactorAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

import java.io.FileNotFoundException;

public class CdscalThresholdFactorAnalysis {

    public static void main(final String[] args) throws ArmStreamException, FileNotFoundException {

        final String outputDestination;

        if (args.length > 0) {
            outputDestination = args[0];
        } else {
            outputDestination = "./";
        }

        final ExperimentWorkspace experimentWorkspace = new ExperimentWorkspace();

        ThresholdFactorAnalysis.executeWithLooseIntegration(
                experimentWorkspace.cdscalMOA3,
                experimentWorkspace.groupedErrorCategorizers,
                outputDestination,
                "cdscal_moa3_threshold_factor_analysis.json");

        ThresholdFactorAnalysis.executeWithLooseIntegration(
                experimentWorkspace.cdscalCovtype,
                experimentWorkspace.groupedErrorCategorizers,
                outputDestination,
                "cdscal_covtype_threshold_factor_analysis.json");

    }

}
