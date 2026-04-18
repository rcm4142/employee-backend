package com.example.employee.integration;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void postEmployee_persistsToDatabase() throws Exception {
        Employee req = new Employee();
        req.setName("Jane");
        req.setEmail("jane@example.com");
        req.setDepartment("HR");

        mockMvc.perform(
                        post("/api/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isCreated());

        assertThat(employeeRepository.findById(1L))
                .isPresent();
    }
}

