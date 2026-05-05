package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.CodingSystem;
import co.thismakesmehappy.fhirgateway.model.fhir.FhirResourceType;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationResource;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationStatus;
import co.thismakesmehappy.fhirgateway.model.internal.InternalObservation;
import co.thismakesmehappy.fhirgateway.model.internal.InternalObservationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FhirObservationMapperTest {
    private final FhirObservationMapper mapper = new FhirObservationMapper();
    private static final String TEST_OBSERVATION_ID = "test-observation-id";
    private static final String TEST_OBSERVATION_PATIENT_ID = "test-observation-patient-id";
    private static final String TEST_OBSERVATION_CODE = "test-observation-code";
    private static final String TEST_OBSERVATION_DISPLAY = "test-observation-display";
    private static final Double TEST_OBSERVATION_VALUE = 123.45;
    private static final String TEST_OBSERVATION_UNIT = "test-observation-unit";
    private static final String TEST_OBSERVATION_UCUM_CODE = "test-observation-ucum-code";
    private static final String TEST_OBSERVATION_TIMESTAMP = "2023-09-20T12:00:00Z";
    private static final InternalObservationStatus TEST_OBSERVATION_STATUS = InternalObservationStatus.FINAL;

    @Test
    void toFhirObservation_withValidObservation_mapsAllFields() {
        InternalObservation observation = new InternalObservation(
                TEST_OBSERVATION_ID,
                TEST_OBSERVATION_PATIENT_ID,
                TEST_OBSERVATION_CODE,
                TEST_OBSERVATION_DISPLAY,
                TEST_OBSERVATION_VALUE,
                TEST_OBSERVATION_UNIT,
                TEST_OBSERVATION_UCUM_CODE,
                TEST_OBSERVATION_TIMESTAMP,
                TEST_OBSERVATION_STATUS
        );

        ObservationResource result = mapper.toFhirObservation(observation);

        assertNotNull(result);
        assertEquals(FhirResourceType.Observation, result.resourceType());
        assertEquals(TEST_OBSERVATION_ID, result.id());
        assertEquals(ObservationStatus.FINAL, result.status());
        List<ObservationResource.Coding> coding = result.code().coding();
        assertEquals(1, coding.size());
        ObservationResource.Coding code = coding.get(0);
        assertEquals(CodingSystem.LOINC, code.system());
        assertEquals(TEST_OBSERVATION_CODE, code.code());
        assertEquals(TEST_OBSERVATION_DISPLAY, code.display());
        assertEquals("Patient/" + TEST_OBSERVATION_PATIENT_ID, result.subject().reference());
        assertEquals(TEST_OBSERVATION_TIMESTAMP, result.effectiveDateTime());
        assertEquals(TEST_OBSERVATION_VALUE, result.valueQuantity().value());
        assertEquals(CodingSystem.UCUM, result.valueQuantity().system());
        assertEquals(TEST_OBSERVATION_UNIT, result.valueQuantity().unit());
        assertEquals(TEST_OBSERVATION_UCUM_CODE, result.valueQuantity().code());
    }

    @ParameterizedTest
    @CsvSource({
            "REGISTERED, registered",
            "PRELIMINARY, preliminary",
            "FINAL, FINAL",
            "AMENDED, amended",
            "CORRECTED, corrected",
            "CANCELLED, cancelled",
            "ENTERED_IN_ERROR, enteredInError",
            "UNKNOWN, unknown"
    })
    void mapStatus_mapsAllStatusValues(InternalObservationStatus internal, ObservationStatus expected) {
        ObservationStatus result = mapper.mapStatus(internal);
        assertEquals(expected, result);
    }

    @Test
    void mapObservation_withNullValue_valueQuantityValueIsNull() {
        InternalObservation observation = new InternalObservation(
                TEST_OBSERVATION_ID, TEST_OBSERVATION_PATIENT_ID, TEST_OBSERVATION_CODE,
                TEST_OBSERVATION_DISPLAY, null, TEST_OBSERVATION_UNIT, TEST_OBSERVATION_UCUM_CODE,
                TEST_OBSERVATION_TIMESTAMP, TEST_OBSERVATION_STATUS
        );
        assertNull(mapper.toFhirObservation(observation).valueQuantity().value());
    }
}