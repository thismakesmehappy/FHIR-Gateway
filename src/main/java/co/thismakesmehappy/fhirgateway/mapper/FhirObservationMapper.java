package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.CodingSystem;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationResource;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationStatus;
import co.thismakesmehappy.fhirgateway.model.internal.InternalObservation;
import co.thismakesmehappy.fhirgateway.model.internal.InternalObservationStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FhirObservationMapper {
    public ObservationResource toFhirObservation(InternalObservation observation) {
        return new ObservationResource(
                observation.id(),
                mapStatus(observation.status()),
                buildCode(observation.code(), observation.display()),
                buildSubject(observation.patientId()),
                observation.timestamp(),
                buildValueQuantity(observation.value(), observation.unit(), observation.ucumCode())
        );
    }

    ObservationStatus mapStatus(InternalObservationStatus status) {
        return switch (status) {
            case REGISTERED -> ObservationStatus.registered;
            case PRELIMINARY -> ObservationStatus.preliminary;
            case FINAL -> ObservationStatus.FINAL;
            case AMENDED -> ObservationStatus.amended;
            case CORRECTED -> ObservationStatus.corrected;
            case CANCELLED -> ObservationStatus.cancelled;
            case ENTERED_IN_ERROR -> ObservationStatus.enteredInError;
            case UNKNOWN -> ObservationStatus.unknown;
        };
    }

    // LOINC is the required coding system for clinical observations per US Core FHIR profile.
    // https://www.hl7.org/fhir/us/core/ValueSet-us-core-observation-value-codes.html
    ObservationResource.CodeableConcept buildCode(String code, String display) {
        return new ObservationResource.CodeableConcept(
                List.of(new ObservationResource.Coding(
                        CodingSystem.LOINC,
                        code,
                        display
                ))
        );
    }

    ObservationResource.Reference buildSubject(String patientId) {
        return new ObservationResource.Reference("Patient/" + patientId);
    }

    ObservationResource.Quantity buildValueQuantity(Double value, String unit, String ucumCode) {
        return new ObservationResource.Quantity(
                value,
                unit,
                CodingSystem.UCUM,
                ucumCode
        );
    }
}
