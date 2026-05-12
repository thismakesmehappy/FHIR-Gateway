package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.CapabilityStatementResource;
import co.thismakesmehappy.fhirgateway.service.CapabilityStatementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fhir")
public class CapabilityStatementController {
    private final CapabilityStatementService capabilityStatementService;

    public CapabilityStatementController(CapabilityStatementService capabilityStatementService) {
        this.capabilityStatementService = capabilityStatementService;
    }

    @GetMapping("/metadata")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CapabilityStatementResource> getMetadata() {
        CapabilityStatementResource metadata = capabilityStatementService.getCapabilityStatement();
        return ResponseEntity.ok().body(metadata);
    }
}
