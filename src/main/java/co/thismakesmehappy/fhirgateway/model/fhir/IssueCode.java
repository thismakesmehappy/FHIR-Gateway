package co.thismakesmehappy.fhirgateway.model.fhir;

/**
 * FHIR OperationOutcome issue code values (subset used by this facade).
 * Lowercase matches FHIR R4 spec string values exactly for direct Jackson serialization.
 * Full value set: https://www.hl7.org/fhir/valueset-issue-type.html
 */
public enum IssueCode {
    @com.fasterxml.jackson.annotation.JsonProperty("not-found")
    notFound,
    required,
    invalid,
    processing,
    unknown
}
