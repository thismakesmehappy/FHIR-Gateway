package co.thismakesmehappy.fhirgateway.model.internal;

public record InternalPatient(
        String id,
        String mrn,
        String firstName,
        String lastName,
        String birthSex,
        String gender,
        String genderIdentity,
        String birthDate
) { }
