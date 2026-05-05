package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FHIR Observation status values.
 * Fixed value set defined by the FHIR R4 spec:
 * https://www.hl7.org/fhir/valueset-observation-status.html
 */

public enum ObservationStatus {
    registered,
    preliminary,
    amended,
    corrected,
    cancelled,
    unknown,
    // "final" is a reserved keyword in Java, dashes are not allowed, so we use the annotations below
    @JsonProperty("entered-in-error")
    enteredInError,
    @JsonProperty("final")
    FINAL
}
