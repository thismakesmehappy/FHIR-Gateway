package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.AdministrativeGender;
import co.thismakesmehappy.fhirgateway.model.fhir.CodingSystem;
import co.thismakesmehappy.fhirgateway.model.fhir.FhirResourceType;
import co.thismakesmehappy.fhirgateway.model.fhir.PatientResource;
import co.thismakesmehappy.fhirgateway.model.internal.BiologicalSex;
import co.thismakesmehappy.fhirgateway.model.internal.Gender;
import co.thismakesmehappy.fhirgateway.model.internal.GenderIdentity;
import co.thismakesmehappy.fhirgateway.model.internal.InternalPatient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FhirPatientMapper {
    private final String mrnSystem;

    public FhirPatientMapper(@Value("${fhir.institution.mrn-system}") String mrnSystem) {
        this.mrnSystem = mrnSystem;
    }

    public PatientResource toFhirPatient(InternalPatient patient) {
        return new PatientResource(
                FhirResourceType.Patient,
                patient.id(),
                buildIdentifiers(patient.mrn()),
                buildNames(patient.lastName(), patient.firstName()),
                mapGender(patient.gender()),
                patient.birthDate(),
                buildExtensions(patient.birthSex(), patient.genderIdentity())
        );
    }

    List<PatientResource.Identifier> buildIdentifiers(String mrn) {
        return List.of(
                new PatientResource.Identifier(mrnSystem, mrn)
        );
    }

    List<PatientResource.HumanName> buildNames(String lastName, String firstName) {
        // Assumes the stored name is the legal name. A richer model would
        // carry nameUse in InternalPatient to support preferred/chosen names.
        return List.of(
                new PatientResource.HumanName(
                        "official",
                        lastName,
                        List.of(firstName)
                )
        );
    }

    AdministrativeGender mapGender(Gender gender) {
        return switch (gender) {
            case FEMALE -> AdministrativeGender.female;
            case MALE -> AdministrativeGender.male;
            case NON_BINARY, OTHER -> AdministrativeGender.other;
            default -> AdministrativeGender.unknown;
        };
    }

    List<PatientResource.Extension> buildExtensions(BiologicalSex birthSex, GenderIdentity genderIdentity) {
        return List.of(
                new PatientResource.Extension(
                        CodingSystem.US_CORE_BIRTH_SEX,
                        birthSex.name()
                ),
                new PatientResource.Extension(
                        CodingSystem.US_CORE_GENDER_IDENTITY,
                        genderIdentity.name()
                )
        );
    }

}
