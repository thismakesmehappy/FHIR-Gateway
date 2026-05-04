package co.thismakesmehappy.fhirgateway.repository;

import co.thismakesmehappy.fhirgateway.model.internal.InternalPatient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PatientRepositoryTest {
    private final PatientRepository repository = new PatientRepository();

    @Test
    void findById_withValidId_returnsPatient() {
        Optional<InternalPatient> result = repository.findById("p1");
        assertTrue(result.isPresent());
        assertEquals("p1", result.get().id());
        assertEquals("Alice", result.get().firstName());
    }

    @Test
    void findById_withUnknownId_returnsEmpty() {
        Optional<InternalPatient> result = repository.findById("unknown");
        assertTrue(result.isEmpty());
    }

    @Test
    void findByMrn_withValidMrn_returnsPatient() {
        List<InternalPatient> results = repository.findByMrn("MRN001");
        assertEquals(1, results.size());
        assertEquals("p1", results.get(0).id());
    }

    @Test
    void findByMrn_withValidMrn_returnsEmptyList() {
        List<InternalPatient> results = repository.findByMrn("unknown");
       assertTrue(results.isEmpty());
    }

    @Test
    void findAll_returnsAllPAtients() {
        List<InternalPatient> result = repository.findAll();
        assertEquals(3, result.size());
    }
}
