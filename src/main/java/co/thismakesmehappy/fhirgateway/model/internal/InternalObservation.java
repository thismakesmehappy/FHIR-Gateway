package co.thismakesmehappy.fhirgateway.model.internal;

public record InternalObservation(
        String id,
        String patientId,
        String code,
        String display,
        Double value,
        String unit,
        String timeStamp
) {
}
