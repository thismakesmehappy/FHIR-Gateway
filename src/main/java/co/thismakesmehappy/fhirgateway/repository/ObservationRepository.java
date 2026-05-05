package co.thismakesmehappy.fhirgateway.repository;

import co.thismakesmehappy.fhirgateway.model.internal.InternalObservation;
import co.thismakesmehappy.fhirgateway.model.internal.InternalObservationStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ObservationRepository {
    private final List<InternalObservation> observations = List.of(
            new InternalObservation("o1", "p1", "8480-6", "Systolic Blood Pressure", 120.0, "mmHg", "2024-01-15T09:00:00Z", InternalObservationStatus.FINAL),
            new InternalObservation("o2", "p1", "8462-4", "Diastolic Blood Pressure", 80.0, "mmHg", "2024-01-15T09:00:00Z", InternalObservationStatus.FINAL),
            new InternalObservation("o3", "p2", "2339-0", "Glucose", 95.0, "mg/dL", "2024-02-10T08:30:00Z", InternalObservationStatus.FINAL),
            new InternalObservation("o4", "p2", "8480-6", "Systolic Blood Pressure", 135.0, "mmHg", "2024-02-10T08:30:00Z", InternalObservationStatus.FINAL),
            new InternalObservation("o5", "p3", "29463-7", "Body Weight", 68.5, "kg", "2024-03-05T11:00:00Z", InternalObservationStatus.FINAL)
    );

    public List<InternalObservation> findByPatientId(String patientId) {
        return observations.stream()
                .filter(observation -> observation.patientId().equals(patientId))
                .toList();
    }

    public List<InternalObservation> findAll() {
        return observations;
    }
}
