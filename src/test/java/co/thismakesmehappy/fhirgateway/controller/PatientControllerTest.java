package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.exception.ResourceNotFoundException;
import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.PatientResource;
import co.thismakesmehappy.fhirgateway.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class PatientControllerTest{
    PatientController patientController;
    @Mock
    PatientService patientService;

    @BeforeEach
    public void setup() {
        patientController = new PatientController(patientService);
    }

    @Test
    public void getPatientById_patientExists_returns200WithPatientResource() {
        PatientResource patientResource = mock(PatientResource.class);
        when(patientService.getPatientById("001")).thenReturn(patientResource);
        ResponseEntity<PatientResource> response = patientController.getPatientById("001");
        assertEquals(patientResource, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        verify(patientService).getPatientById("001");
    }

    @Test
    public void getPatientById_patientDoesNotExists_raisesResourceNotFoundException() {
        when(patientService.getPatientById("001")).thenThrow(new ResourceNotFoundException("Patient", "001"));
        assertThrows(ResourceNotFoundException.class, () -> patientController.getPatientById("001"));
        verify(patientService).getPatientById("001");
    }

    @Test
    public void searchPatientsByIdentifier_identifierExists_returns200WithBundleResource() {
        BundleResource<PatientResource> bundleResource = mock(BundleResource.class);
        when(patientService.searchPatientsByIdentifier(("001"))).thenReturn(bundleResource);
        ResponseEntity<BundleResource<PatientResource>> response = patientController.searchPatientsByIdentifier("001");
        assertEquals(bundleResource, response.getBody());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        verify(patientService).searchPatientsByIdentifier("001");
    }


}