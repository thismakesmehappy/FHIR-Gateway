package co.thismakesmehappy.fhirgateway.model.fhir;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TypeRestfulInteraction {
    read,           // read a single resource by id
    vread,          // read a specific version
    update,         // full update
    patch,          // partial update
    delete,         // delete
    create,         // create new resource
    @JsonProperty("history-instance")
    history_instance,  // version history for one resource
    @JsonProperty("history-type")
    history_type,      // version history for a type
    @JsonProperty("search-type")
    search_type        // search with query params
}
