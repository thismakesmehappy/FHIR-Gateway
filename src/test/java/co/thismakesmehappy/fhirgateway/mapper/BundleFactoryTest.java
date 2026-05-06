package co.thismakesmehappy.fhirgateway.mapper;

import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.FhirResourceType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BundleFactoryTest {
    BundleFactory bundleFactory = new BundleFactory();

    @Test
    void buildSearchBundle_withEmptyEntries() {
        List<String> patientList = List.of();
        BundleResource<String> bundle = bundleFactory.buildSearchBundle(patientList);

        assertNotNull(bundle);
        assertEquals(0, bundle.total());
        assertEquals(FhirResourceType.Bundle, bundle.resourceType());
        assertEquals("searchset", bundle.type());
        assertEquals(0, bundle.entry().size());
    }

    @Test
    void buildSearchBundle_withSingleEntry() {
        List<String> patientList = List.of("patient1");
        BundleResource<String> bundle = bundleFactory.buildSearchBundle(patientList);

        assertNotNull(bundle);
        assertEquals(1, bundle.total());
        assertEquals(FhirResourceType.Bundle, bundle.resourceType());
        assertEquals("searchset", bundle.type());
        assertEquals(1, bundle.entry().size());
        assertEquals(patientList.get(0), bundle.entry().get(0).resource());
    }

    @Test
    void buildSearchBundle_withMultipleEntries() {
        List<String> patientList = List.of("patient1", "patient2", "patient3", "patient4", "patient5");
        BundleResource<String> bundle = bundleFactory.buildSearchBundle(patientList);

        assertNotNull(bundle);
        assertEquals(5, bundle.total());
        assertEquals(FhirResourceType.Bundle, bundle.resourceType());
        assertEquals("searchset", bundle.type());

        List<String> actualResource = bundle.entry().stream()
                .map(BundleResource.BundleEntry::resource)
                .toList();

        assertEquals(patientList, actualResource);
    }
}
