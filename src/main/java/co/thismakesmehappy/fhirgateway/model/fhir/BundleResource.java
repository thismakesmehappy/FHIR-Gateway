package co.thismakesmehappy.fhirgateway.model.fhir;

import java.util.List;

/**
 * FHIR Bundle resource — wraps multiple resources for search results.
 * type "searchset" is used for all search responses per FHIR R4 spec.
 */

public record BundleResource<T>(
        FhirResourceType resourceType,
        int total,
        List<BundleEntry<T>> entry
) {
    public String type() { return "searchset"; }
    public record BundleEntry<T>(T resource) {}
}
