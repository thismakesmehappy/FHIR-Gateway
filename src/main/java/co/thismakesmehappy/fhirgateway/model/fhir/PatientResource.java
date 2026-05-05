package co.thismakesmehappy.fhirgateway.model.fhir;

import java.util.List;

public record PatientResource(
        FhirResourceType resourceType,
        String id,
        List<Identifier> identifier,
        List<HumanName> name,
        AdministrativeGender gender,
        String birthDate,
        List<Extension> extension
) {
    public record Identifier(String system, String value) {}
    public record HumanName(String use, String family, List<String> given) {}
    public record Extension(String url, String valueCode) {}
}
