package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationResource;
import co.thismakesmehappy.fhirgateway.service.ObservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class ObservationControllerTest {
    ObservationController observationController;
    @Mock
    ObservationService observationService;

    @BeforeEach
    public void setup() {
        observationController = new ObservationController(observationService);
    }

    @Test
    public void searchObservationByPatient_patientExists_returns200WithBundleResource() {
        BundleResource<ObservationResource> bundleResource = mock(BundleResource.class);
        when(observationService.searchObservationsByPatient("Patient/123")).thenReturn(bundleResource);
        ResponseEntity<BundleResource<ObservationResource>> response = observationController.searchObservationsByPatient("Patient/123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bundleResource, response.getBody());
        verify(observationService).searchObservationsByPatient("Patient/123");
    }
}
