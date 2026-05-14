package co.thismakesmehappy.fhirgateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "FHIR Gateway API",
				version = "1.0.0",
				description = "A gateway service for accessing FHIR resources"
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "Local development server"),
				@Server(url = "http://localhost:8000", description = "Local Kong proxy")
		}
)
@SpringBootApplication
public class FhirGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FhirGatewayApplication.class, args);
	}

}
