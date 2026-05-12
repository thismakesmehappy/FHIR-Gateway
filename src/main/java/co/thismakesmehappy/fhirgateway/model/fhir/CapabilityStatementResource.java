package co.thismakesmehappy.fhirgateway.model.fhir;

import java.util.List;

public record CapabilityStatementResource(
    FhirResourceType resourceType,
    PublicationStatus status,
    CapabilityStatementKind kind,
    FhirVersion fhirVersion,
    List<FhirFormat> format,
    List<Rest> rest
) {
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
