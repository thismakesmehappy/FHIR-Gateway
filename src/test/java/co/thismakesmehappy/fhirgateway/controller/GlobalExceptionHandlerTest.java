package co.thismakesmehappy.fhirgateway.controller;

import co.thismakesmehappy.fhirgateway.exception.ResourceNotFoundException;
import co.thismakesmehappy.fhirgateway.mapper.OperationOutcomeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OperationOutcomeFactory.class, GlobalExceptionHandler.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private MockMvc mockMvc;

    @RestController
    static class TestController {
        @GetMapping("/test/not-found")
        public void throwNotFound() {
            throw new ResourceNotFoundException("Patient", "p1");
        }

        @GetMapping("/test/missing-param")
        public void requireParam(@RequestParam String identifier) {}

        @GetMapping("/test/internal-error")
        public void throwGeneric() {
            throw new RuntimeException("something went wrong");
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    void whenResourceNotFound_returns404WithOperationOutcome() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resourceType").value("OperationOutcome"))
                .andExpect(jsonPath("$.issue[0].severity").value("error"))
                .andExpect(jsonPath("$.issue[0].code").value("not-found"))
                .andExpect(jsonPath("$.issue[0].diagnostics").value("Patient with id 'p1' not found"));
    }

    @Test
    void whenMissingRequiredParam_returns400WithOperationOutcome() throws Exception {
        mockMvc.perform(get("/test/missing-param"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.resourceType").value("OperationOutcome"))
                .andExpect(jsonPath("$.issue[0].severity").value("error"))
                .andExpect(jsonPath("$.issue[0].code").value("required"))
                .andExpect(jsonPath("$.issue[0].diagnostics").value("Required parameter 'identifier' is missing"));
    }

    @Test
    void whenUnhandledException_returns500WithOperationOutcome() throws Exception {
        mockMvc.perform(get("/test/internal-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.resourceType").value("OperationOutcome"))
                .andExpect(jsonPath("$.issue[0].severity").value("fatal"))
                .andExpect(jsonPath("$.issue[0].code").value("processing"))
                .andExpect(jsonPath("$.issue[0].diagnostics").value("something went wrong"));
    }
}