package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.OperationOutcomeResource;
import co.thismakesmehappy.fhirgateway.model.fhir.PatientResource;
import co.thismakesmehappy.fhirgateway.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fhir")
@Tag(name = "Patient", description = "Operations related to managing patient resources")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/Patient/{id}")
    @Operation(summary = "Retrieve a patient by ID", description = "Returns a FHIR Patient resource for the specified patient ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Patient resource retrieved successfully",
                    content = @Content(mediaType = "application/fhir+json",
                            schema = @Schema(implementation = PatientResource.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "Patient not found",
                    content = @Content(mediaType = "application/fhir+json",
                            schema = @Schema(implementation = OperationOutcomeResource.class)

                    )
            )
    })
    public ResponseEntity<PatientResource> getPatientById(@PathVariable String id) {
        PatientResource patient = patientService.getPatientById(id);
        return ResponseEntity.ok().body(patient);
    }

    @GetMapping("/Patient")
    @Operation(summary = "Search patients by identifier", description = "Returns a FHIR Bundle resource containing Patient resources matching the specified identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Patients found successfully",
                    content = @Content(mediaType = "application/fhir+json",
                            schema = @Schema(ref = "#/components/schemas/PatientBundle")
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Missing required parameter",
                    content = @Content(mediaType = "application/fhir+json",
                            schema = @Schema(implementation = OperationOutcomeResource.class)
                    )
            )
    })
    public ResponseEntity<BundleResource<PatientResource>> searchPatientsByIdentifier(@RequestParam("identifier") String identifier) {
        BundleResource<PatientResource> patients = patientService.searchPatientsByIdentifier(identifier);
        return ResponseEntity.ok().body(patients);
    }
}
