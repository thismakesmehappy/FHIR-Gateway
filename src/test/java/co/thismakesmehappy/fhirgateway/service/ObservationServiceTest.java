package co.thismakesmehappy.fhirgateway.service;

import co.thismakesmehappy.fhirgateway.mapper.BundleFactory;
import co.thismakesmehappy.fhirgateway.mapper.FhirObservationMapper;
import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationResource;
import co.thismakesmehappy.fhirgateway.model.internal.InternalObservation;
import co.thismakesmehappy.fhirgateway.model.internal.InternalObservationStatus;
import co.thismakesmehappy.fhirgateway.repository.ObservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObservationServiceTest {
    @Mock
    private ObservationRepository observationRepository;

    @Mock
    private FhirObservationMapper observationMapper;

    @Mock
    private BundleFactory bundleFactory;

    @InjectMocks
    private ObservationService observationService;

    private static final InternalObservation INTERNAL_OBSERVATION = new InternalObservation(
            "obs-001",
            "Patient/pat-001",
            "55284-4",
            "Blood Pressure",
            120.0,
            "mmHg",
            "mm[Hg]",
            "2024-09-15T10:30:00Z",
            InternalObservationStatus.FINAL
    );

    @Test
    void searchObservationsByPatientId_withValidId_returnsMappedObservation() {
        ObservationResource mockResource = mock(ObservationResource.class);
        BundleResource<ObservationResource> mockBundle = mock(BundleResource.class);
        when(observationRepository.findByPatientId("Patient/pat-001")).thenReturn(List.of(INTERNAL_OBSERVATION));
        when(observationMapper.toFhirObservation(INTERNAL_OBSERVATION)).thenReturn(mockResource);
        when(bundleFactory.buildSearchBundle(List.of(mockResource))).thenReturn(mockBundle);

        BundleResource<ObservationResource> result = observationService.searchObservationsByPatient("Patient/pat-001");
        assertEquals(mockBundle, result);
        verify(observationRepository).findByPatientId("Patient/pat-001");
        verify(observationMapper).toFhirObservation(INTERNAL_OBSERVATION);
    }

    @Test
    void searchObservationsByPatientId_withUnknownId_returnsEmptyBundle() {
        BundleResource<ObservationResource> mockBundle = mock(BundleResource.class);
        when(observationRepository.findByPatientId("UNKNOWN")).thenReturn(List.of());
        when(bundleFactory.<ObservationResource>buildSearchBundle(List.of())).thenReturn(mockBundle);

        BundleResource<ObservationResource> result = observationService.searchObservationsByPatient("UNKNOWN");

        assertEquals(mockBundle, result);
        verify(observationRepository).findByPatientId("UNKNOWN");
        verify(bundleFactory).buildSearchBundle(List.of());
        verify(observationMapper, never()).toFhirObservation(any());
    }

}
