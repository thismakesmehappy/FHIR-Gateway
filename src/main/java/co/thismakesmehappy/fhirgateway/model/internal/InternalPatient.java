package co.thismakesmehappy.fhirgateway.model.internal;

public record InternalPatient(
        String id,
        String mrn,
        String firstName,
        String lastName,
        BiologicalSex birthSex,
        Gender gender,
        GenderIdentity genderIdentity,
        String birthDate
) { }
