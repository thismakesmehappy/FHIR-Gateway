package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * FHIR Bundle resource — wraps multiple resources for search results.
 * type "searchset" is used for all search responses per FHIR R4 spec.
 */

public record BundleResource<T>(
        int total,
        List<BundleEntry<T>> entry
) {
    public static final FhirResourceType resourceType = FhirResourceType.Bundle;

    @JsonProperty("resourceType")
    public FhirResourceType resourceType() { return resourceType; }
    public String type() { return "searchset"; }
    public record BundleEntry<T>(T resource) {}
}
