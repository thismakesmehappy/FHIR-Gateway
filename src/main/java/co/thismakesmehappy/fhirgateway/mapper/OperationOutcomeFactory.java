package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.FhirResourceType;
import co.thismakesmehappy.fhirgateway.model.fhir.IssueCode;
import co.thismakesmehappy.fhirgateway.model.fhir.IssueSeverity;
import co.thismakesmehappy.fhirgateway.model.fhir.OperationOutcomeResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationOutcomeFactory {

    public OperationOutcomeResource notFound(String diagnostics) {
        return build(IssueSeverity.error, IssueCode.notFound, diagnostics);
    }

    public OperationOutcomeResource notFound(List<String> diagnostics) {
        return buildMultiple(IssueSeverity.error, IssueCode.notFound, diagnostics);
    }

    public OperationOutcomeResource badRequest(String diagnostics) {
        return build(IssueSeverity.error, IssueCode.required, diagnostics);
    }

    public OperationOutcomeResource badRequest(List<String> diagnostics) {
        return buildMultiple(IssueSeverity.error, IssueCode.required, diagnostics);
    }

    public OperationOutcomeResource internalError(String diagnostics) {
        return build(IssueSeverity.fatal, IssueCode.processing, diagnostics);
    }

    public OperationOutcomeResource internalError(List<String> diagnostics) {
        return buildMultiple(IssueSeverity.fatal, IssueCode.processing, diagnostics);
    }

    private OperationOutcomeResource build(IssueSeverity severity, IssueCode code, String diagnostics) {
        return new OperationOutcomeResource(
                FhirResourceType.OperationOutcome,
                List.of(new OperationOutcomeResource.Issue(severity, code, diagnostics))
        );
    }

    // All issues in a multi-issue outcome share the same severity and code.
    // For mixed-severity outcomes, use a method that accepts List<Issue> directly.
    private OperationOutcomeResource buildMultiple(IssueSeverity severity, IssueCode code, List<String> diagnostics) {
        List<OperationOutcomeResource.Issue> issues = diagnostics.stream()
                .map(d -> new OperationOutcomeResource.Issue(severity, code, d))
                .toList();
        return new OperationOutcomeResource(FhirResourceType.OperationOutcome, issues);
    }
}
