package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.FhirResourceType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BundleFactory {

    public <T> BundleResource<T> buildSearchBundle(List<T> resources) {
        List<BundleResource.BundleEntry<T>> entries = resources.stream()
                .map(BundleResource.BundleEntry::new)
                .toList();

        return new BundleResource<>(
                FhirResourceType.Bundle,
                resources.size(),
                entries
        );
    }
}
