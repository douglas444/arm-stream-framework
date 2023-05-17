package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.exp.experiments.IntegrationAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

import java.io.FileNotFoundException;

public class EchoLooseIntegration {

    public static void main(final String[] args) throws ArmStreamException, FileNotFoundException {

        final String outputDestination;

        if (args.length > 0) {
            outputDestination = args[0];
        } else {
            outputDestination = "./";
        }

        final ExperimentWorkspace experimentWorkspace = new ExperimentWorkspace();

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.echoMOA3,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "echo_moa3_loose_integration_report.json",
                "echo_moa3_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.echoKDD99,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "echo_kdd99_loose_integration_report.json",
                "echo_kdd99_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.echoCovtype,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "echo_covtype_loose_integration_report.json",
                "echo_covtype_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.echoSynEDC,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "echo_synedc_loose_integration_report.json",
                "echo_synedc_loose_integration_logs.json");

    }

}