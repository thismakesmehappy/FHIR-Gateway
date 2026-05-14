package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PatientResource(
        String id,
        List<Identifier> identifier,
        List<HumanName> name,
        AdministrativeGender gender,
        String birthDate,
        List<Extension> extension
) {
    public static final FhirResourceType resourceType = FhirResourceType.Patient;

    @JsonProperty("resourceType")
    public FhirResourceType resourceType() { return resourceType; }
    public record Identifier(String system, String value) {}
    public record HumanName(String use, String family, List<String> given) {}
    public record Extension(String url, String valueCode) {}
}
