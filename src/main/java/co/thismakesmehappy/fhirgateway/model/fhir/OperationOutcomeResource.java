package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * FHIR OperationOutcome resource — returned for errors and validation failures.
 * Used for 400, 404, and 500 responses per FHIR R4 spec.
 */

public record OperationOutcomeResource(
        List<Issue> issue
) {
    public static final FhirResourceType resourceType = FhirResourceType.OperationOutcome;

    @JsonProperty("resourceType")
    public FhirResourceType resourceType() { return resourceType; }

    public record Issue(
            IssueSeverity severity,
            IssueCode code,
            String diagnostics
    ) {}
}