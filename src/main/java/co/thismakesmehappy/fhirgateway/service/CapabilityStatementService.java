package co.thismakesmehappy.fhirgateway.service;

import co.thismakesmehappy.fhirgateway.model.fhir.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapabilityStatementService {
    public CapabilityStatementResource getCapabilityStatement() {
        return new CapabilityStatementResource(
                PublicationStatus.active,
                CapabilityStatementKind.instance,
                FhirVersion.v4_0_1,
                List.of(FhirFormat.json),
                List.of(new CapabilityStatementResource.Rest(
                        RestfulCapabilityMode.server,
                        List.of(
                                new CapabilityStatementResource.Resource(
                                        FhirResourceType.Patient,
                                        List.of(
                                                new CapabilityStatementResource.Interaction(TypeRestfulInteraction.read),
                                                new CapabilityStatementResource.Interaction(TypeRestfulInteraction.search_type
                                                )
                                        )
                                ),
                                new CapabilityStatementResource.Resource(
                                        FhirResourceType.Observation,
                                        List.of(new CapabilityStatementResource.Interaction(TypeRestfulInteraction.search_type)
                                        )
                                )
                        )
                )
                )
        );
    }
}
