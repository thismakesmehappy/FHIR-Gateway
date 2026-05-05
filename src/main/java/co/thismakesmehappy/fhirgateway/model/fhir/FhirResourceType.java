package co.thismakesmehappy.fhirgateway.model.fhir;

/**
 * FHIR resources types supported by this facade.
 * PascalCase matches the FHIR spec's resourceType string values exactly,
 * allowing Jackson to serialize enum names directly without a custom serializer.
 * The full FHIR R4 spec defines ~150 resource types.
 */
public enum FhirResourceType {
    Patient,
    Observation,
    Bundle,
    OperationOutcome,
    CapabilityStatement
}
