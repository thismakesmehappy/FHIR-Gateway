package co.thismakesmehappy.fhirgateway.model.fhir;

/**
 * FHIR OperationOutcome issue severity values.
 * Lowercase matches FHIR R4 spec string values exactly for direct Jackson serialization.
 * Fixed value set: https://www.hl7.org/fhir/valueset-issue-severity.html
 */

public enum IssueSeverity {
    fatal,
    error,
    warning,
    information
}
