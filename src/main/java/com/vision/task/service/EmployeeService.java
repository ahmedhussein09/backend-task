package com.vision.task.service;

import com.vision.task.dao.entity.Employee;
import com.vision.task.dao.repository.EmployeeRepository;
import com.vision.task.exception.BusinessException;
import com.vision.task.integration.ThirdPartyIntegration;
import com.vision.task.model.EmployeeDto;
import com.vision.task.utils.files.TaskConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;
    private final ThirdPartyIntegration thirdPartyIntegrationImpl;

    public void addEmployee(@Valid EmployeeDto employeeDto){
        if(!thirdPartyIntegrationImpl.validationEmail(employeeDto.getEmail()))
            throw new BusinessException(TaskConst.INVALID_EMAIL);
        if(employeeRepository.findByEmail(employeeDto.getEmail()).isPresent())
            throw new BusinessException(TaskConst.EMPLOYEE_ALREADY_EXIST);
        Employee employee = new Employee(employeeDto);
        employeeRepository.save(employee);
        emailSenderService.sendNotificationEmail(employee.getEmail());
    }

    public List<EmployeeDto> getAllEmployee() {
        return employeeRepository.findAll().stream().map(EmployeeDto::new).toList();
    }

    public EmployeeDto getEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()-> new BusinessException(TaskConst.EMPLOYEE_NOT_FOUND));
        return new EmployeeDto(employee);
    }

    public void editEmployee(long employeeId, @Valid EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()-> new BusinessException(TaskConst.EMPLOYEE_NOT_FOUND));
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employee.getDepartment());
        employee.setSalary(employeeDto.getSalary());
        employeeRepository.save(employee);
    }

    public void deleteEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()-> new BusinessException(TaskConst.EMPLOYEE_NOT_FOUND));
        employeeRepository.delete(employee);
    }
}
