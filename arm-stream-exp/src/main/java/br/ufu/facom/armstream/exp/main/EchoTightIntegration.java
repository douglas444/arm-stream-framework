package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.exp.experiments.IntegrationAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

import java.io.FileNotFoundException;

public class EchoTightIntegration {

    public static void main(final String[] args) throws ArmStreamException, FileNotFoundException {

        final String outputDestination;

        if (args.length > 0) {
            outputDestination = args[0];
        } else {
            outputDestination = "./";
        }

        final ExperimentWorkspace experimentWorkspace = new ExperimentWorkspace();

        //MKCN

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoMOA3,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "echo_moa3_mkcn_tight_integration_report.json",
                "echo_moa3_mkcn_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoSynEDC,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "echo_synedc_mkcn_tight_integration_report.json",
                "echo_synedc_mkcn_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoKDD99,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "echo_kdd99_mkcn_tight_integration_report.json",
                "echo_kdd99_mkcn_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoCovtype,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "echo_covtype_mkcn_tight_integration_report.json",
                "echo_covtype_mkcn_tight_integration_logs.json");

        //MKR

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoMOA3,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "echo_moa3_mkr_tight_integration_report.json",
                "echo_moa3_mkr_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoSynEDC,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "echo_synedc_mkr_tight_integration_report.json",
                "echo_synedc_mkr_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoKDD99,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "echo_kdd99_mkr_tight_integration_report.json",
                "echo_kdd99_mkr_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.echoCovtype,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "echo_covtype_mkr_tight_integration_report.json",
                "echo_covtype_mkr_tight_integration_logs.json");

    }

}