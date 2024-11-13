package com.vision.task;

import com.vision.task.dao.entity.Employee;
import com.vision.task.dao.repository.EmployeeRepository;
import com.vision.task.exception.BusinessException;
import com.vision.task.model.EmployeeDto;
import com.vision.task.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addEmployeeSuccess() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmail("ahmed@gmail.com");
        employeeDto.setFirstName("Ahmed");
        employeeDto.setLastName("Hussein");
        employeeDto.setDepartment("CS");
        employeeDto.setSalary(2000);
        when(employeeRepository.save(any())).thenReturn(new Employee());

        Assertions.assertDoesNotThrow(() -> employeeService.addEmployee(employeeDto));
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void addDuplicatedEmployee() {
        EmployeeDto duplicatedEmployeeDto = new EmployeeDto();
        duplicatedEmployeeDto.setEmail("ahmed@gmail.com");
        duplicatedEmployeeDto.setFirstName("Ahmed");
        duplicatedEmployeeDto.setLastName("Hussein");
        duplicatedEmployeeDto.setDepartment("CS");
        duplicatedEmployeeDto.setSalary(2000);
        when(employeeRepository.findByEmail(duplicatedEmployeeDto.getEmail())).thenReturn(Optional.empty());
        employeeService.addEmployee(duplicatedEmployeeDto);
        when(employeeRepository.findByEmail(duplicatedEmployeeDto.getEmail())).thenReturn(Optional.of(new Employee(duplicatedEmployeeDto)));
        Exception exception = assertThrows(BusinessException.class, () -> employeeService.addEmployee(duplicatedEmployeeDto));

        assertEquals("1", exception.getMessage());
    }

    /*@Test
    void getAllEmployee() {
        List<Employee> employees = List.of(new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getEmployee();
        assertEquals(1, result.size());
        assertEquals(employees, result);
    }

    // Test for getEmployee when no employees exist
    @Test
    void getEmployee_noEmployees_returnsEmptyList() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = employeeService.getEmployee();
        assertTrue(result.isEmpty());
    }*/
}
