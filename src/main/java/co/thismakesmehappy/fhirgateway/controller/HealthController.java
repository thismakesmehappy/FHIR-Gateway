package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.HealthStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Health", description = "Operations related to checking the health of the FHIR gateway")
public class HealthController {
    @GetMapping("/healthz")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Check the health of the FHIR gateway", description = "Returns a simple health check response.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Health check successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HealthStatus.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                description = "Health check failed",
                content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                )
        )
    })
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
