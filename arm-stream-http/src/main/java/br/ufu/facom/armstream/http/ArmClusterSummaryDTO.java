package br.ufu.facom.armstream.http;

import br.ufu.facom.armstream.api.datastructure.ArmClusterSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArmClusterSummaryDTO implements ArmClusterSummary {

    @JsonProperty("centroid_attributes")
    private double[] centroidAttributes;
    @JsonProperty("standard_deviation")
    private double standardDeviation;
    @JsonProperty("label")
    private Integer label;

    public ArmClusterSummaryDTO() {
    }

    public ArmClusterSummaryDTO(final double[] centroidAttributes, final double standardDeviation, final Integer label) {
        this.centroidAttributes = centroidAttributes;
        this.standardDeviation = standardDeviation;
        this.label = label;
    }

    @Override
    public double[] getCentroidAttributes() {
        return centroidAttributes;
    }

    public void setCentroidAttributes(final double[] centroidAttributes) {
        this.centroidAttributes = centroidAttributes;
    }

    @Override
    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(final double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    @Override
    public Integer getLabel() {
        return label;
    }

    public void setLabel(final Integer label) {
        this.label = label;
    }
}
