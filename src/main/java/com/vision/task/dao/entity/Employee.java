package com.vision.task.dao.entity;

import com.vision.task.model.EmployeeDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private double salary;
    public Employee(EmployeeDto employeeDto){
        this.firstName = employeeDto.getFirstName();
        this.lastName = employeeDto.getLastName();
        this.department = employeeDto.getDepartment();
        this.email = employeeDto.getEmail();
        this.salary = employeeDto.getSalary();
    }
}
