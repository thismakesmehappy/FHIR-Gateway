package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationResource;
import co.thismakesmehappy.fhirgateway.model.fhir.OperationOutcomeResource;
import co.thismakesmehappy.fhirgateway.service.ObservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Observation", description = "Operations related to FHIR Observations")
@RequestMapping("/fhir")
public class ObservationController {
    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping("/Observation")
    @Operation(summary = "Search FHIR Observations by patient ID", description = "Returns a bundle of FHIR Observations for a given patient.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Observations found successfully",
                    content = @Content(mediaType = "application/fhir+json",
                            schema = @Schema(ref = "#/components/schemas/ObservationBundle"))),
            @ApiResponse(responseCode = "400",
                    description = "Missing required parameter",
                    content = @Content(mediaType = "application/fhir+json",
                            schema = @Schema(implementation = OperationOutcomeResource.class))
            ),
    })
    public ResponseEntity<BundleResource<ObservationResource>> searchObservationsByPatient(@RequestParam("patient") String patient) {
        BundleResource<ObservationResource> observations = observationService.searchObservationsByPatient(patient);
        return ResponseEntity.ok().body(observations);
    }
}
