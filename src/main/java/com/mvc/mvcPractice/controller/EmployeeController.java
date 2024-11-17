package com.mvc.mvcPractice.controller;

import com.mvc.mvcPractice.dto.RequestEmployeeDto;
import com.mvc.mvcPractice.dto.ResponseEmployeeDto;
import com.mvc.mvcPractice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService EmpService;

    @GetMapping(path = "/hello")
    public String getHandler(){
        return "Hello";
    }

    @GetMapping(path = "/employees/{id}")
    public ResponseEmployeeDto getEmp(@PathVariable(name = "id") int empId){
        return EmpService.getEmpData(new RequestEmployeeDto(empId,"email.com","name",21));
    }

    @GetMapping(path="/allEmployees")
    public List<ResponseEmployeeDto> getAllEmps(){
        return EmpService.getAllEmps();
    }

    @PostMapping(path = "/employee")
    public ResponseEntity<String> createEmployee(@RequestBody RequestEmployeeDto emp){
        System.out.println("emp to create "+ emp.toString());
        return ResponseEntity.status(200).body(EmpService.CreateEmp(emp));
    }

    @PutMapping(path="/employee/{id}")
    public ResponseEmployeeDto updateEmp(@RequestBody RequestEmployeeDto emp,@PathVariable int id) throws Exception {
        try {
            return EmpService.updateEmp(emp,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    REDIRECTION EXAMPLE

    @GetMapping("/OldUrl")
    public ResponseEntity oldHandler(){
//        client will automatically redirect to new url with help of Location header
        return ResponseEntity.status(301).header("Location","/newUrl").build();
    }

    @GetMapping("/newUrl")
    public ResponseEntity<String> NewHandler(){
        return ResponseEntity.status(200).body("Welcome");
    }

}