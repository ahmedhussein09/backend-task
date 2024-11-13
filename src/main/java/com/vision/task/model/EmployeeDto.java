package com.vision.task.model;

import com.vision.task.dao.entity.Employee;
import com.vision.task.utils.files.TaskConst;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private long employeeId;
    @NotNull(message = TaskConst.FIRST_NAME_IS_REQUIRED)
    private String firstName;
    @NotNull(message = TaskConst.LAST_NAME_IS_REQUIRED)
    private String lastName;
    @NotNull(message = TaskConst.EMAIL_IS_REQUIRED)
    /*@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = TaskConst.INVALID_EMAIL)*/
    private String email;
    @NotNull(message = TaskConst.DEPARTMENT_IS_REQUIRED)
    private String department;
    @Min(value = 1000, message = TaskConst.SALARY_MIN)
    @Max(value = 10000, message = TaskConst.SALARY_MAX)
    private double salary;

    public EmployeeDto(Employee employee){
        this.employeeId = employee.getEmployeeId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.department = employee.getDepartment();
        this.email = employee.getEmail();
        this.salary = employee.getSalary();
    }
}
