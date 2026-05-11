package co.thismakesmehappy.fhirgateway.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceType;
    private final String id;

    public ResourceNotFoundException(String resourceType, String id) {
        super(resourceType + " with id '" + id + "' not found");
        this.resourceType = resourceType;
        this.id = id;
    }
}