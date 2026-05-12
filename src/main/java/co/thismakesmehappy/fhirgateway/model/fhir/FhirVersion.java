package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FHIR specification versions.
 * Full version history: https://www.hl7.org/fhir/history.html
 */
public enum FhirVersion {
    @JsonProperty("0.0.82")
    v0_0_82,  // DSTU1 dinal
    @JsonProperty("1.0.2")
    v1_0_2,   // DSTU2 final
    @JsonProperty("3.0.2")
    v3_0_2,   // STU3 final
    @JsonProperty("4.0.1")
    v4_0_1,   // R4 final — current standard
    @JsonProperty("4.3.0")
    v4_3_0,   // R4B final
    @JsonProperty("5.0.0")
    v5_0_0,   // R5 final
    @JsonProperty("6.0.0")
    v6_0_0    // R6 draft
}
