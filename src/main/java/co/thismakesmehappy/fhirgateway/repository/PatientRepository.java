package co.thismakesmehappy.fhirgateway.repository;

import co.thismakesmehappy.fhirgateway.model.internal.BiologicalSex;
import co.thismakesmehappy.fhirgateway.model.internal.Gender;
import co.thismakesmehappy.fhirgateway.model.internal.GenderIdentity;
import co.thismakesmehappy.fhirgateway.model.internal.InternalPatient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepository {
    private final List<InternalPatient> patients = List.of(
            new InternalPatient("p1", "MRN001", "Alice", "Smith", BiologicalSex.FEMALE, Gender.FEMALE, GenderIdentity.FEMALE, "1985-03-22"),
            new InternalPatient("p2", "MRN002", "Bob", "Jones", BiologicalSex.MALE, Gender.MALE, GenderIdentity.MALE, "1972-11-04"),
            new InternalPatient("p3", "MRN003", "Taylor", "Rivera", BiologicalSex.FEMALE, Gender.NON_BINARY, GenderIdentity.NON_BINARY, "1990-07-15"),
            new InternalPatient("p4", "MRN003", "Lala", "Rocha", BiologicalSex.FEMALE, Gender.NON_BINARY, GenderIdentity.NON_BINARY, "1990-07-15")
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
