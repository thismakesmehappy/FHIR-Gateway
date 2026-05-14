package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ObservationResource(
        String id,
        ObservationStatus status,
        CodeableConcept code,
        Reference subject,
        String effectiveDateTime,
        Quantity valueQuantity
) {
    public static final FhirResourceType resourceType = FhirResourceType.Observation;

    @JsonProperty("resourceType")
    public FhirResourceType resourceType() { return resourceType; }

    public record CodeableConcept(List<Coding> coding) {}
    public record Coding(String system, String code, String display) {}
    public record Reference(String reference) {}
    public record Quantity(Double value, String unit, String system, String code) {}
}
