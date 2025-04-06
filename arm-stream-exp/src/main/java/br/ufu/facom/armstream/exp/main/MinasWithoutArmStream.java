package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.exp.experiments.IntegrationAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

public class MinasWithoutArmStream {

    public static void main(final String[] args) throws Exception {

        final String outputDestination;

        if (args.length > 0) {
            outputDestination = args[0];
        } else {
            outputDestination = "./";
        }

        final ExperimentWorkspace experimentWorkspace = new ExperimentWorkspace();

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.minasMOA3,
                outputDestination,
                "minas_moa3_logs.json");

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.minasSynEDC,
                outputDestination,
                "minas_synedc_logs.json");

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.minasKDD99,
                outputDestination,
                "minas_kdd99_logs.json");

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.minasCovtype,
                outputDestination,
                "minas_covtype_logs.json");

    }

}