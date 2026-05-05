package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.AdministrativeGender;
import co.thismakesmehappy.fhirgateway.model.fhir.CodingSystem;
import co.thismakesmehappy.fhirgateway.model.fhir.FhirResourceType;
import co.thismakesmehappy.fhirgateway.model.fhir.PatientResource;
import co.thismakesmehappy.fhirgateway.model.internal.BiologicalSex;
import co.thismakesmehappy.fhirgateway.model.internal.Gender;
import co.thismakesmehappy.fhirgateway.model.internal.GenderIdentity;
import co.thismakesmehappy.fhirgateway.model.internal.InternalPatient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FhirPatientMapperTest {
    private static final String MRN_SYSTEM = "urn:test:mrn";
    private final FhirPatientMapper mapper = new FhirPatientMapper(MRN_SYSTEM);

    private static final String TEST_PATIENT_ID = "test-patient-id";
    private static final String TEST_PATIENT_FIRST_NAME = "Test Patient";
    private static final String TEST_PATIENT_LAST_NAME = "Test Last Name";
    private static final BiologicalSex TEST_PATIENT_BIRTH_SEX = BiologicalSex.NOT_ASKED;
    private static final Gender TEST_PATIENT_GENDER = Gender.NOT_RECORDED;
    private static final GenderIdentity TEST_PATIENT_GENDER_IDENTITY = GenderIdentity.NOT_RECORDED;
    private static final String TEST_PATIENT_BIRTHDATE = "1990-01-01";
    private static final String TEST_PATIENT_MRN = "1234567890";

    @Test
    void toFhirPatient_withValidPatient_mapsAllFields() {
        InternalPatient patient = new InternalPatient(
                TEST_PATIENT_ID,
                TEST_PATIENT_MRN,
                TEST_PATIENT_FIRST_NAME,
                TEST_PATIENT_LAST_NAME,
                TEST_PATIENT_BIRTH_SEX,
                TEST_PATIENT_GENDER,
                TEST_PATIENT_GENDER_IDENTITY,
                TEST_PATIENT_BIRTHDATE
        );

        PatientResource result = mapper.toFhirPatient(patient);

        assertNotNull(result);
        assertEquals(FhirResourceType.Patient, result.resourceType());
        assertEquals(TEST_PATIENT_ID, result.id());
        PatientResource.Identifier identifier = result.identifier().get(0);
        assertEquals(MRN_SYSTEM, identifier.system());
        assertEquals(TEST_PATIENT_MRN, identifier.value());
        PatientResource.HumanName name = result.name().get(0);
        assertEquals("official", name.use());
        assertEquals(TEST_PATIENT_FIRST_NAME, name.given().get(0));
        assertEquals(TEST_PATIENT_LAST_NAME, name.family());
        assertEquals(AdministrativeGender.unknown, result.gender());
        assertEquals(TEST_PATIENT_BIRTHDATE, result.birthDate());
        PatientResource.Extension birthSex = result.extension().get(0);
        PatientResource.Extension genderIdentity = result.extension().get(1);
        assertEquals(CodingSystem.US_CORE_BIRTH_SEX, birthSex.url());
        assertEquals(String.valueOf(TEST_PATIENT_BIRTH_SEX), birthSex.valueCode());
        assertEquals(CodingSystem.US_CORE_GENDER_IDENTITY, genderIdentity.url());
        assertEquals(String.valueOf(TEST_PATIENT_GENDER_IDENTITY), genderIdentity.valueCode());

    }


    @ParameterizedTest
    @CsvSource({
            "FEMALE, female",
            "MALE, male",
            "NON_BINARY, other",
            "UNKNOWN, unknown",
            "DECLINED, unknown",
            "NOT_ASKED, unknown",
            "NOT_RECORDED, unknown",
            "OTHER, other"
    })
    void mapGender_mapsAllGenderValues(Gender internal, AdministrativeGender expected) {
        AdministrativeGender result = mapper.mapGender(internal);
        assertEquals(expected, result);
    }


}