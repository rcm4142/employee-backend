package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EmployeeService employeeService;

    @Test
    void create_returns201_andCreatedEmployee() throws Exception {
        Employee saved = new Employee();
        saved.setId(1L);
        saved.setName("Ramesh");
        saved.setEmail("ramesh@example.com");
        saved.setDepartment("IT");

        when(employeeService.create(ArgumentMatchers.any(Employee.class))).thenReturn(saved);

        Employee req = new Employee();
        req.setName("Ramesh");
        req.setEmail("ramesh@example.com");
        req.setDepartment("IT");

        mockMvc.perform(
                        post("/api/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ramesh"))
                .andExpect(jsonPath("$.email").value("ramesh@example.com"))
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Test
    void create_withInvalidPayload_returns400() throws Exception {
        Employee req = new Employee();
        req.setName("");
        req.setEmail("not-an-email");
        req.setDepartment("");

        mockMvc.perform(
                        post("/api/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                )
                .andExpect(status().isBadRequest());
    }
}

