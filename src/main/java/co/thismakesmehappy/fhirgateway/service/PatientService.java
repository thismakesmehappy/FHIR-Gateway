package co.thismakesmehappy.fhirgateway.service;

import co.thismakesmehappy.fhirgateway.exception.ResourceNotFoundException;
import co.thismakesmehappy.fhirgateway.mapper.BundleFactory;
import co.thismakesmehappy.fhirgateway.mapper.FhirPatientMapper;
import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.PatientResource;
import co.thismakesmehappy.fhirgateway.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final FhirPatientMapper patientMapper;
    private final BundleFactory bundleFactory;

    public PatientService(
            PatientRepository patientRepository,
            FhirPatientMapper patientMapper,
            BundleFactory bundleFactory
    ) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.bundleFactory = bundleFactory;
    }

    public PatientResource getPatientById(String id) {
        return patientRepository.findById(id)
                .map(patientMapper::toFhirPatient)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    public BundleResource<PatientResource> searchPatientsByIdentifier(String identifier) {
        List<PatientResource> patients = patientRepository.findByMrn(identifier)
                .stream()
                .map(patientMapper::toFhirPatient)
                .toList();
        return bundleFactory.buildSearchBundle(patients);
    }
}
