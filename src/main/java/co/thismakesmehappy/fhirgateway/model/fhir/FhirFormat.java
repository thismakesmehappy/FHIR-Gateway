package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FhirFormat {
    @JsonProperty("application/fhir+json")
    json,
    @JsonProperty("application/fhir+xml")
    xml
}
