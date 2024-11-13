package com.vision.task.controller;


import com.vision.task.model.EmployeeDto;
import com.vision.task.model.ResponsePojo;
import com.vision.task.service.EmployeeService;
import com.vision.task.utils.files.TaskConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/")
    ResponseEntity<ResponsePojo> addEmployee(@RequestBody @Valid EmployeeDto employeeDto){
        employeeService.addEmployee(employeeDto);
        return new ResponseEntity<>(new ResponsePojo(true, TaskConst.SUCCESS_EN, TaskConst.SUCCESS_AR, null),HttpStatus.CREATED);
    }
    @GetMapping("/")
    ResponseEntity<ResponsePojo> getAllEmployee(){
        return new ResponseEntity<>(new ResponsePojo(true, TaskConst.SUCCESS_EN, TaskConst.SUCCESS_AR, employeeService.getAllEmployee()), HttpStatus.OK);
    }
    @GetMapping("/{employeeId}")
    ResponseEntity<ResponsePojo> getEmployee(@PathVariable long employeeId){
        return new ResponseEntity<>(new ResponsePojo(true, TaskConst.SUCCESS_EN, TaskConst.SUCCESS_AR, employeeService.getEmployee(employeeId)), HttpStatus.OK);
    }
    @PutMapping("/{employeeId}")
    ResponseEntity<ResponsePojo> editEmployee(@PathVariable long employeeId, @RequestBody @Valid EmployeeDto employeeDto ){
        employeeService.editEmployee(employeeId, employeeDto);
        return new ResponseEntity<>(new ResponsePojo(true, TaskConst.SUCCESS_EN, TaskConst.SUCCESS_AR,null), HttpStatus.OK);
    }
    @DeleteMapping("/{employeeId}")
    ResponseEntity<ResponsePojo> deleteEmployee(@PathVariable long employeeId){
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(new ResponsePojo(true, TaskConst.SUCCESS_EN, TaskConst.SUCCESS_AR, null), HttpStatus.OK);
    }
}
