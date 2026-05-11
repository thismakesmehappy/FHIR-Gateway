package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.exception.ResourceNotFoundException;
import co.thismakesmehappy.fhirgateway.mapper.OperationOutcomeFactory;
import co.thismakesmehappy.fhirgateway.model.fhir.OperationOutcomeResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final OperationOutcomeFactory operationOutcomeFactory;

    public GlobalExceptionHandler(OperationOutcomeFactory operationOutcomeFactory) {
        this.operationOutcomeFactory = operationOutcomeFactory;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<OperationOutcomeResource> handleNotFound(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(operationOutcomeFactory.notFound(exception.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<OperationOutcomeResource> handleMissingParameter(MissingServletRequestParameterException exception) {
        String message = "Required parameter '" + exception.getParameterName() + "' is missing";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(operationOutcomeFactory.badRequest(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OperationOutcomeResource> handleGenericException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(operationOutcomeFactory.internalError(exception.getMessage()));
    }
}
