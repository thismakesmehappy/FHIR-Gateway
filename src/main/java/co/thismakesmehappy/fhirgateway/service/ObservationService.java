package co.thismakesmehappy.fhirgateway.service;

import co.thismakesmehappy.fhirgateway.mapper.BundleFactory;
import co.thismakesmehappy.fhirgateway.mapper.FhirObservationMapper;
import co.thismakesmehappy.fhirgateway.model.fhir.BundleResource;
import co.thismakesmehappy.fhirgateway.model.fhir.ObservationResource;
import co.thismakesmehappy.fhirgateway.repository.ObservationRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ObservationService {
    private final ObservationRepository observationRepository;
    private final FhirObservationMapper observationMapper;
    private final BundleFactory bundleFactory;

    public ObservationService(
            ObservationRepository observationRepository,
            FhirObservationMapper observationMapper,
            BundleFactory bundleFactory
    ) {
        this.observationRepository = observationRepository;
        this.observationMapper = observationMapper;
        this.bundleFactory = bundleFactory;
    }

    public BundleResource<ObservationResource> searchObservationsByPatient(String patientId) {
        List<ObservationResource> observations =
                observationRepository.findByPatientId(patientId)
                        .stream()
                        .map(observationMapper::toFhirObservation)
                        .toList();

        return bundleFactory.buildSearchBundle(observations);
    }
}
