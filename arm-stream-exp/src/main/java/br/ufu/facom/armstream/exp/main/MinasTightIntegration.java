package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.exp.experiments.IntegrationAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

import java.io.FileNotFoundException;

public class MinasTightIntegration {

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
                experimentWorkspace.minasMOA3,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "minas_moa3_mkcn_tight_integration_report.json",
                "minas_moa3_mkcn_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.minasSynEDC,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "minas_synedc_mkcn_tight_integration_report.json",
                "minas_synedc_mkcn_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.minasKDD99,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "minas_kdd99_mkcn_tight_integration_report.json",
                "minas_kdd99_mkcn_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.minasCovtype,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[0],
                outputDestination,
                "minas_covtype_mkcn_tight_integration_report.json",
                "minas_covtype_mkcn_tight_integration_logs.json");

        //MKR

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.minasMOA3,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "minas_moa3_mkr_tight_integration_report.json",
                "minas_moa3_mkr_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.minasSynEDC,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "minas_synedc_mkr_tight_integration_report.json",
                "minas_synedc_mkr_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.minasKDD99,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "minas_kdd99_mkr_tight_integration_report.json",
                "minas_kdd99_mkr_tight_integration_logs.json");

        IntegrationAnalysis.executeTightIntegration(
                experimentWorkspace.minasCovtype,
                experimentWorkspace.metaCategorizers[2],
                experimentWorkspace.activeCategorizers[2],
                outputDestination,
                "minas_covtype_mkr_tight_integration_report.json",
                "minas_covtype_mkr_tight_integration_logs.json");

    }

}