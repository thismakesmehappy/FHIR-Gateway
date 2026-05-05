package co.thismakesmehappy.fhirgateway.model.fhir;

import java.util.List;

public record ObservationResource(
        FhirResourceType resourceType,
        String id,
        ObservationStatus status,
        CodeableConcept code,
        Reference subject,
        String effectiveDateTime,
        Quantity valueQuantity
) {
    public record CodeableConcept(List<Coding> coding) {}
    public record Coding(String system, String code, String display) {}
    public record Reference(String reference) {}
    public record Quantity(Double value, String unit, String system, String code) {}
}
