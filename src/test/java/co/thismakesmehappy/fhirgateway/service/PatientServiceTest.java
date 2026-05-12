package co.thismakesmehappy.fhirgateway.service;

import co.thismakesmehappy.fhirgateway.exception.ResourceNotFoundException;
import co.thismakesmehappy.fhirgateway.mapper.BundleFactory;
import co.thismakesmehappy.fhirgateway.mapper.FhirPatientMapper;
import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.PatientResource;
import co.thismakesmehappy.fhirgateway.model.internal.BiologicalSex;
import co.thismakesmehappy.fhirgateway.model.internal.Gender;
import co.thismakesmehappy.fhirgateway.model.internal.GenderIdentity;
import co.thismakesmehappy.fhirgateway.model.internal.InternalPatient;
import co.thismakesmehappy.fhirgateway.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private FhirPatientMapper patientMapper;

    @Mock
    private BundleFactory bundleFactory;

    @InjectMocks
    private PatientService patientService;

    private static final InternalPatient INTERNAL_PATIENT = new InternalPatient(
            "p1", "MRN001", "Alice", "Smith", BiologicalSex.FEMALE, Gender.FEMALE, GenderIdentity.FEMALE, "1985-03-22"
    );

    @Test
    void getPatientById_withValidId_returnsMappedPatient() {
        PatientResource mockResource = mock(PatientResource.class);
        when(patientRepository.findById("p1")).thenReturn((Optional.of(INTERNAL_PATIENT)));
        when(patientMapper.toFhirPatient(INTERNAL_PATIENT)).thenReturn(mockResource);

        PatientResource result = patientService.getPatientById("p1");

        assertEquals(mockResource, result);
        verify(patientRepository).findById("p1");

        verify(patientMapper).toFhirPatient(INTERNAL_PATIENT);
    }

    @Test
    void getPatientId_withUnknownId_throwsResourceNotFoundException() {
        when(patientRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.getPatientById("unknown"));
        verify(patientRepository).findById("unknown");
    }

    @Test
    void searchPatientByIdentifier_withValidMrn_returnsMappedBundle() {
        PatientResource mockResource = mock(PatientResource.class);
        BundleResource<PatientResource> mockBundle = mock(BundleResource.class);
        when(patientRepository.findByMrn("MRN001")).thenReturn(List.of(INTERNAL_PATIENT));
        when(patientMapper.toFhirPatient(INTERNAL_PATIENT)).thenReturn(mockResource);
        when(bundleFactory.buildSearchBundle(List.of(mockResource))).thenReturn(mockBundle);

        BundleResource<PatientResource> result = patientService.searchPatientsByIdentifier("MRN001");

        assertEquals(mockBundle, result);
        verify(patientRepository).findByMrn("MRN001");

        verify(patientMapper).toFhirPatient(INTERNAL_PATIENT);
        verify(bundleFactory).buildSearchBundle(List.of(mockResource));
    }

    @Test
    void searchPatientByIdentifier_withUnknownMrn_returnsEmptyBundle() {
        BundleResource<PatientResource> mockBundle = mock(BundleResource.class);
        when(patientRepository.findByMrn("UNKNOWN")).thenReturn(List.of());
        when(bundleFactory.<PatientResource>buildSearchBundle(List.of())).thenReturn(mockBundle);

        BundleResource<PatientResource> result = patientService.searchPatientsByIdentifier("UNKNOWN");

        assertEquals(mockBundle, result);
        verify(patientRepository).findByMrn("UNKNOWN");
        verify(bundleFactory).buildSearchBundle(List.of());
        verify(patientMapper, never()).toFhirPatient(any());
    }
}
