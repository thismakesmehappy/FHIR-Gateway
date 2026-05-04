package co.thismakesmehappy.fhirgateway.repository;

import co.thismakesmehappy.fhirgateway.model.internal.InternalPatient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {
    private final List<InternalPatient> patients = List.of(
            new InternalPatient("p1", "MR001", "Alice", "Smith", "female", "female", "female", "1985-93-22"),
            new InternalPatient("p2", "MR002", "Bob", "Jones", "male", "male", "male", "1972-11-04"),
            new InternalPatient("p3", "MR003", "Taylow", "Rivera", "female", "non-binary", "non-binary", "1990-07-15")
    );

    public Optional<InternalPatient> findById(String id) {
        return patients.stream()
                .filter(patient -> patient.id().equals(id))
                .findFirst();
    }

    public List<InternalPatient> findByMrn(String mrn) {
        return patients.stream()
                .filter(patient -> patient.mrn().equals(mrn))
                .toList();
    }

    public List<InternalPatient> findAll() {
        return patients;
    }

}
