# FHIR Gateway

A FHIR R4 facade over internal patient data, with Kong API Gateway handling authentication, rate limiting, and routing.

## Architecture

```
Client → Kong (:8100) → FHIR Gateway (:8080) → In-memory data
```

Kong handles all gateway concerns (auth, rate limiting, correlation IDs). The Spring Boot service focuses purely on FHIR transformation logic.

## Run

```bash
docker compose up --build
```

Both services start together. Kong is available on port 8100, the app directly on 8080.

## Endpoints

All requests through Kong require the header `apikey: dev-secret-key`.

| Method | Path | Description |
|--------|------|-------------|
| GET | /healthz | Health check |
| GET | /fhir/metadata | FHIR capability statement |
| GET | /fhir/Patient/{id} | Get patient by ID |
| GET | /fhir/Patient?identifier= | Search patients by MRN |
| GET | /fhir/Observation?patient= | Search observations by patient ID |

## Examples

```bash
# Health check
curl http://localhost:8100/healthz -H 'apikey: dev-secret-key'

# Get patient by ID
curl http://localhost:8100/fhir/Patient/p1 -H 'apikey: dev-secret-key'

# Search by MRN
curl "http://localhost:8100/fhir/Patient?identifier=MRN001" -H 'apikey: dev-secret-key'

# Search observations
curl "http://localhost:8100/fhir/Observation?patient=p1" -H 'apikey: dev-secret-key'

# Missing key — returns 401
curl http://localhost:8100/fhir/Patient/p1
```

## Ports

| Port | Service |
|------|---------|
| 8080 | FHIR Gateway (direct, bypasses Kong) |
| 8100 | Kong proxy |
| 8001 | Kong admin API |

## API Docs

When running locally, the auto-generated OpenAPI spec is available at:

- http://localhost:8080/v3/api-docs
- http://localhost:8080/swagger-ui.html