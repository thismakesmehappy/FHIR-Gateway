package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.CapabilityStatementResource;
import co.thismakesmehappy.fhirgateway.service.CapabilityStatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class CapabilityStatementControllerTest {

    @Mock
    CapabilityStatementService capabilityStatementService;
    CapabilityStatementController capabilityStatementController;

    @BeforeEach
    public void setUp() {
        capabilityStatementController = new CapabilityStatementController(capabilityStatementService);
    }

    @Test
    public void getMetadata_returns200WithCapabilityStatement() {
        CapabilityStatementResource capabilityStatementResource = mock(CapabilityStatementResource.class);
        when(capabilityStatementService.getCapabilityStatement()).thenReturn(capabilityStatementResource);
        ResponseEntity<CapabilityStatementResource> response = capabilityStatementController.getMetadata();
        assertEquals(capabilityStatementResource, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        verify(capabilityStatementService).getCapabilityStatement();



    }
}
