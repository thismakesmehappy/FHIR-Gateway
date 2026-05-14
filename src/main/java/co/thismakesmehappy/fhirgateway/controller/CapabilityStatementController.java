package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.CapabilityStatementResource;
import co.thismakesmehappy.fhirgateway.service.CapabilityStatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Capability", description = "Operations related to retrieving the FHIR server's capability statement")
@RequestMapping("/fhir")
public class CapabilityStatementController {
    private final CapabilityStatementService capabilityStatementService;

    public CapabilityStatementController(CapabilityStatementService capabilityStatementService) {
        this.capabilityStatementService = capabilityStatementService;
    }

    @GetMapping("/metadata")
    @Operation(summary = "Retrieve the FHIR server's capability statement", description = "Returns the metadata describing the FHIR server's capabilities and supported features.")
    @ApiResponse(responseCode = "200",
            description = "Successful retrieval of capability statement",
            content = @Content(mediaType = "application/fhir+json",
                    schema = @Schema(implementation = CapabilityStatementResource.class)
            )
    )
    public ResponseEntity<CapabilityStatementResource> getMetadata() {
        CapabilityStatementResource metadata = capabilityStatementService.getCapabilityStatement();
        return ResponseEntity.ok().body(metadata);
    }
}
