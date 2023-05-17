package br.ufu.facom.armstream.exp.main;

import br.ufu.facom.armstream.core.ArmStreamException;
import br.ufu.facom.armstream.exp.experiments.IntegrationAnalysis;
import br.ufu.facom.armstream.exp.workspace.ExperimentWorkspace;

import java.io.FileNotFoundException;

public class CdscalLooseIntegration {

    public static void main(final String[] args) throws ArmStreamException, FileNotFoundException {

        final String outputDestination;

        if (args.length > 0) {
            outputDestination = args[0];
        } else {
            outputDestination = "./";
        }

        final ExperimentWorkspace experimentWorkspace = new ExperimentWorkspace();

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.cdscalMOA3,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "cdscal_moa3_loose_integration_report.json",
                "cdscal_moa3_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.cdscalKDD99,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "cdscal_kdd99_loose_integration_report.json",
                "cdscal_kdd99_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.cdscalCovtype,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "cdscal_covtype_loose_integration_report.json",
                "cdscal_covtype_loose_integration_logs.json");

        IntegrationAnalysis.executeLooseIntegration(
                experimentWorkspace.cdscalSynEDC,
                experimentWorkspace.metaCategorizers,
                experimentWorkspace.activeCategorizers,
                outputDestination,
                "cdscal_synedc_loose_integration_report.json",
                "cdscal_synedc_loose_integration_logs.json");

    }
}
