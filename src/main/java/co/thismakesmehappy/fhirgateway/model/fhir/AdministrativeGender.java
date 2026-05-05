package co.thismakesmehappy.fhirgateway.model.fhir;

/**
 * FHIR administrative gender values.
 * Lowercase matches FHIR R4 spec string values exactly for direct Jackson
 serialization.
 * Fixed value set:
 https://www.hl7.org/fhir/valueset-administrative-gender.html
 */

public enum AdministrativeGender {
    male,
    female,
    other,
    unknown
}
