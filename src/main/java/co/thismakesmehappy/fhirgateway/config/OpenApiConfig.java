package co.thismakesmehappy.fhirgateway.config;

import co.thismakesmehappy.fhirgateway.model.fhir.*;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer registerFhirBundleSchemas() {
        return openApi -> {
            ResolvedSchema patientBundle = ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new AnnotatedType(new TypeReference<BundleResource<PatientResource>>() {}.getType()));
            ResolvedSchema observationBundle = ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new AnnotatedType(new TypeReference<BundleResource<ObservationResource>>() {}.getType()));

            openApi.getComponents().addSchemas("PatientBundle", patientBundle.schema);
            openApi.getComponents().addSchemas("ObservationBundle", observationBundle.schema);

            patientBundle.schema.setExample(Map.of(
                    "resourceType", "Bundle",
                    "total", 1,
                    "entry", List.of(Map.of(
                            "resource", Map.of(
                                    "resourceType", "Patient",
                                    "id", "p1",
                                    "name", List.of(Map.of("family", "Smith", "given", List.of("Alice"))),
                                    "gender", "female",
                                    "birthDate", "1985-03-22"
                            )
                    ))
            ));
            observationBundle.schema.setExample(Map.of(
                    "resourceType", "Bundle",
                    "total", 1,
                    "entry", List.of(Map.of(
                            "resourceType", "Observation",
                            "id", "o1",
                            "status", "final",
                            "code", Map.of(
                                    "coding", List.of(
                                            Map.of(
                                                    "system", "http://loinc.org",
                                                    "code", "8480-6",
                                                    "display", "Systolic Blood Pressure"
                                            )
                                    )
                            ),
                            "subject", Map.of(
                                    "reference", "Patient/p1"
                            ),
                            "effectiveDateTime", "2023-09-15T12:00:00Z",
                            "valueQuantity", Map.of(
                                    "value", 120,
                                    "unit", "mmHg",
                                    "system", "http://unitsofmeasure.org",
                                    "code", "mm[Hg]"
                            )

            ))
            )
            );
        };
    }
}
