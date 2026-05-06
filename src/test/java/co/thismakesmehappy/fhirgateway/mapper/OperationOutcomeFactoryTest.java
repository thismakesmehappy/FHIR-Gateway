package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.FhirResourceType;
import co.thismakesmehappy.fhirgateway.model.fhir.IssueCode;
import co.thismakesmehappy.fhirgateway.model.fhir.IssueSeverity;
import co.thismakesmehappy.fhirgateway.model.fhir.OperationOutcomeResource;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OperationOutcomeFactoryTest {

    private final OperationOutcomeFactory factory = new OperationOutcomeFactory();

    @Test
    void notFound_singleDiagnostic_returnsCorrectOutcome() {
        OperationOutcomeResource result = factory.notFound("Patient p99 not found");

        assertEquals(FhirResourceType.OperationOutcome, result.resourceType());
        assertEquals(1, result.issue().size());
        OperationOutcomeResource.Issue issue = result.issue().get(0);
        assertEquals(IssueSeverity.error, issue.severity());
        assertEquals(IssueCode.notFound, issue.code());
        assertEquals("Patient p99 not found", issue.diagnostics());
    }

    @Test
    void notFound_multipleDiagnostics_returnsAllIssues() {
        List<String> diagnostics = List.of("Patient p99 not found", "Patient p100 not found");
        OperationOutcomeResource result = factory.notFound(diagnostics);

        assertEquals(FhirResourceType.OperationOutcome, result.resourceType());
        assertEquals(2, result.issue().size());
        result.issue().forEach(issue -> {
            assertEquals(IssueSeverity.error, issue.severity());
            assertEquals(IssueCode.notFound, issue.code());
        });
        assertEquals("Patient p99 not found", result.issue().get(0).diagnostics());
        assertEquals("Patient p100 not found", result.issue().get(1).diagnostics());
    }

    @Test
    void badRequest_singleDiagnostic_returnsCorrectOutcome() {
        OperationOutcomeResource result = factory.badRequest("Required parameter 'identifier' is missing");

        assertEquals(FhirResourceType.OperationOutcome, result.resourceType());
        assertEquals(1, result.issue().size());
        OperationOutcomeResource.Issue issue = result.issue().get(0);
        assertEquals(IssueSeverity.error, issue.severity());
        assertEquals(IssueCode.required, issue.code());
        assertEquals("Required parameter 'identifier' is missing", issue.diagnostics());
    }

    @Test
    void badRequest_multipleDiagnostics_returnsAllIssues() {
        List<String> diagnostics = List.of("Parameter 'identifier' is missing", "Parameter 'system' is missing");
        OperationOutcomeResource result = factory.badRequest(diagnostics);

        assertEquals(2, result.issue().size());
        result.issue().forEach(issue -> {
            assertEquals(IssueSeverity.error, issue.severity());
            assertEquals(IssueCode.required, issue.code());
        });
    }

    @Test
    void internalError_singleDiagnostic_returnsCorrectOutcome() {
        OperationOutcomeResource result = factory.internalError("Unexpected server error");

        assertEquals(FhirResourceType.OperationOutcome, result.resourceType());
        assertEquals(1, result.issue().size());
        OperationOutcomeResource.Issue issue = result.issue().get(0);
        assertEquals(IssueSeverity.fatal, issue.severity());
        assertEquals(IssueCode.processing, issue.code());
        assertEquals("Unexpected server error", issue.diagnostics());
    }

    @Test
    void internalError_multipleDiagnostics_returnsAllIssues() {
        List<String> diagnostics = List.of("Database connection failed", "Timeout exceeded");
        OperationOutcomeResource result = factory.internalError(diagnostics);

        assertEquals(2, result.issue().size());
        result.issue().forEach(issue -> {
            assertEquals(IssueSeverity.fatal, issue.severity());
            assertEquals(IssueCode.processing, issue.code());
        });
    }
}
