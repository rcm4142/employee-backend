package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Test
    void create_nullsIdBeforeSave() {
        EmployeeRepository repo = mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repo);

        Employee input = new Employee();
        input.setId(99L);
        input.setName("A");
        input.setEmail("a@example.com");
        input.setDepartment("IT");

        when(repo.save(input)).thenAnswer(inv -> inv.getArgument(0));

        service.create(input);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
    }
}

