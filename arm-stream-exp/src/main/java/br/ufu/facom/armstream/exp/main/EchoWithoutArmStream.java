package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.exp.experiments.IntegrationAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

public class EchoWithoutArmStream {

    public static void main(final String[] args) throws Exception {

        final String outputDestination;

        if (args.length > 0) {
            outputDestination = args[0];
        } else {
            outputDestination = "./";
        }

        final ExperimentWorkspace experimentWorkspace = new ExperimentWorkspace();

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.echoMOA3,
                outputDestination,
                "echo_moa3_logs.json");

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.echoSynEDC,
                outputDestination,
                "echo_synedc_logs.json");

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.echoKDD99,
                outputDestination,
                "echo_kdd99_logs.json");

        IntegrationAnalysis.executeWithNoIntegration(
                experimentWorkspace.echoCovtype,
                outputDestination,
                "echo_covtype_logs.json");

    }

}