package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CapabilityStatementResource(
        PublicationStatus status,
    CapabilityStatementKind kind,
    FhirVersion fhirVersion,
    List<FhirFormat> format,
    List<Rest> rest
) {
    public static final FhirResourceType resourceType = FhirResourceType.CapabilityStatement;

    @JsonProperty("resourceType")
    public FhirResourceType resourceType() { return resourceType; }

    public record Rest(
            RestfulCapabilityMode mode,
            List<Resource> resource
    ) {}

    public record Resource(
            FhirResourceType type,
            List<Interaction> interaction
    ) {}

    public record Interaction(TypeRestfulInteraction code) {}
}
