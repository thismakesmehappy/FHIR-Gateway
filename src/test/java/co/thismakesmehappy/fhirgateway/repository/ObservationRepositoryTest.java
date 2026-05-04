package co.thismakesmehappy.fhirgateway.repository;

import co.thismakesmehappy.fhirgateway.model.internal.InternalObservation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ObservationRepositoryTest {
    private final ObservationRepository repository = new ObservationRepository();

    @Test
    void findPatientId_withValidPatientId_returnsObservations() {
        List<InternalObservation> result = repository.findByPatientId("p1");
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(obs -> obs.patientId().equals("p1")));
    }

    @Test
    void findPatientId_withUnknownPatientId_returnsEmptyList() {
        List<InternalObservation> result = repository.findByPatientId("unkown");
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_returnAllObservations() {
        List<InternalObservation> result = repository.findAll();
        assertEquals(5, result.size());
    }
}
