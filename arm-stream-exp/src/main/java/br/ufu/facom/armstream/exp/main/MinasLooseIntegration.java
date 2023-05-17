package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.exp.experiments.IntegrationAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

import java.io.FileNotFoundException;

public class MinasLooseIntegration {

    public static void main(final String[] args) throws ArmStreamException, FileNotFoundException {

        final String outputDestination;

        if (args.length > 0) {
            outputDestination = args[0];
        } else {
            outputDestination = "./";
        }

        final ExperimentWorkspace experimentWorkspace = new ExperimentWorkspace();

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.minasMOA3,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "minas_moa3_loose_integration_report.json",
                "minas_moa3_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.minasSynEDC,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "minas_synedc_loose_integration_report.json",
                "minas_synedc_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.minasKDD99,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "minas_kdd99_loose_integration_report.json",
                "minas_kdd99_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.minasCovtype,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "minas_covtype_loose_integration_report.json",
                "minas_covtype_loose_integration_logs.json");
    }

}