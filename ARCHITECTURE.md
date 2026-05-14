# Architecture

## Overview

This system exposes a FHIR R4 API over internal patient data through two layers: a Spring Boot facade service and a Kong API Gateway in front of it.

```
Client → Kong (:8100) → FHIR Gateway (:8080) → In-memory data
```

## Layers

### Kong API Gateway

Kong sits in front of the application and handles all gateway concerns:

- **Authentication** — API key validation via the `key-auth` plugin. Requests without a valid key are rejected with `401` before reaching the app.
- **Rate limiting** — capped at 10 requests per minute per consumer via the `rate-limiting` plugin.
- **Correlation ID** — every request is assigned a `X-Correlation-ID` header for tracing via the `correlation-id` plugin.

Kong is configured in DB-less mode using a declarative `kong.yml` file. No database is required.

### FHIR Gateway (Spring Boot)

The backend service translates internal domain records into FHIR R4 resources. It does not store FHIR objects — it exposes a FHIR-compatible view over simpler internal data.

Key responsibilities:
- map `InternalPatient` → FHIR `Patient`
- map `InternalObservation` → FHIR `Observation`
- wrap search results in FHIR `Bundle`
- return FHIR `OperationOutcome` for errors
- serve a `CapabilityStatement` at `/fhir/metadata`

### Internal Data

Patient and observation records are stored as in-memory Java objects in repository classes. The internal model is intentionally different from FHIR — it uses flat fields like `firstName`, `lastName`, and `mrn` rather than FHIR's nested structures. The mapper layer is where that translation happens.

## Design Decisions

**Why a facade instead of storing FHIR directly?**
Internal systems rarely use FHIR natively. A facade lets the internal model evolve independently while presenting a stable FHIR interface to consumers.

**Why gateway auth instead of Spring Security?**
For this system, authentication is a gateway concern, not an application concern. Kong intercepts and validates keys before requests reach the app, keeping the backend stateless and focused on data logic.

**Why DB-less Kong?**
DB-less mode keeps the local setup simple — one config file, no migrations, no additional container. It is the right model for learning gateway concepts and for reproducible local development.

## Request Flow Example

```
GET /fhir/Patient/p1
  → Kong validates apikey header
  → Kong injects X-Correlation-ID
  → Kong forwards to http://fhir-gateway:8080/fhir/Patient/p1
  → PatientController calls PatientService
  → PatientService calls PatientRepository, maps result via FhirPatientMapper
  → returns FHIR Patient JSON with status 200
```