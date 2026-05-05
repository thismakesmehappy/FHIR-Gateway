package co.thismakesmehappy.fhirgateway.model.fhir;

import java.util.List;

/**
 * FHIR OperationOutcome resource — returned for errors and validation failures.
 * Used for 400, 404, and 500 responses per FHIR R4 spec.
 */

public record OperationOutcomeResource(
        FhirResourceType resourceType,
        List<Issue> issue
) {
    public record Issue(
            IssueSeverity severity,
            IssueCode code,
            String diagnostics
    ) {}
}
