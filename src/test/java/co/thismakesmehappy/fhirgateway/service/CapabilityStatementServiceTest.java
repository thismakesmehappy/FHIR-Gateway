package co.thismakesmehappy.fhirgateway.service;

import co.thismakesmehappy.fhirgateway.model.fhir.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapabilityStatementServiceTest {
    final CapabilityStatementService capabilityStatementService = new CapabilityStatementService();
    final CapabilityStatementResource capabilityStatement = capabilityStatementService.getCapabilityStatement();
    final List<CapabilityStatementResource.Rest> rest = capabilityStatement.rest();

    @Test
    void getCapabilityStatement_hasCorrectMetadata() {
        assertEquals(FhirResourceType.CapabilityStatement, capabilityStatement.resourceType());
        assertEquals(PublicationStatus.active, capabilityStatement.status());
        assertEquals(CapabilityStatementKind.instance, capabilityStatement.kind());
        assertEquals(FhirVersion.v4_0_1, capabilityStatement.fhirVersion());
        List<FhirFormat> format = capabilityStatement.format();
        assertEquals(1, format.size());
        assertEquals(FhirFormat.json, format.get(0));
        assertEquals(1, rest.size());
        assertEquals(RestfulCapabilityMode.server, rest.get(0).mode());
        assertEquals(2, rest.get(0).resource().size());
    }

    @Test
    void getCapabilityStatement_hasPatientWithCorrectInteractions() {
        CapabilityStatementResource.Resource patient = rest.get(0).resource().get(0);
        assertEquals(FhirResourceType.Patient, patient.type());
        assertEquals(List.of(
                new CapabilityStatementResource.Interaction(TypeRestfulInteraction.read),
                new CapabilityStatementResource.Interaction(TypeRestfulInteraction.search_type
                )
        ), patient.interaction());
    }

    @Test
    void getCapabilityStatement_hasObservationWithCorrectInteractions() {
        CapabilityStatementResource.Resource observation = rest.get(0).resource().get(1);
        assertEquals(FhirResourceType.Observation, observation.type());
        assertEquals(List.of(
                new CapabilityStatementResource.Interaction(TypeRestfulInteraction.search_type
                )
        ), observation.interaction());
    }
}
