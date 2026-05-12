package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.PatientResource;
import co.thismakesmehappy.fhirgateway.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fhir")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/Patient/{id}")
    public ResponseEntity<PatientResource> getPatientById(@PathVariable String id) {
        PatientResource patient = patientService.getPatientById(id);
        return ResponseEntity.ok().body(patient);
    }

    @GetMapping("/Patient")
    public ResponseEntity<BundleResource<PatientResource>> searchPatientsByIdentifier(@RequestParam("identifier") String identifier) {
        BundleResource<PatientResource> patients = patientService.searchPatientsByIdentifier(identifier);
        return ResponseEntity.ok().body(patients);
    }
}
