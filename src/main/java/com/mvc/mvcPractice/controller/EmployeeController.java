package com.mvc.mvcPractice.controller;

import com.mvc.mvcPractice.CustomValidator.groups.groupA;
import com.mvc.mvcPractice.dto.RequestEmployeeDto;
import com.mvc.mvcPractice.dto.ResponseEmployeeDto;
import com.mvc.mvcPractice.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<ResponseEmployeeDto> createEmployee(@RequestBody @Valid RequestEmployeeDto emp){
        System.out.println("emp to create "+ emp.toString());
        Optional<ResponseEmployeeDto> resp=EmpService.CreateEmp(emp); //Optional.isEmpty() for throwing exceptions
        return resp.map((r)->ResponseEntity.ok(r)).orElseThrow(()->new RuntimeException("error on creating employee"));
    }

    @PutMapping(path="/employee/{id}")
    public ResponseEmployeeDto updateEmp(@RequestBody @Validated RequestEmployeeDto emp, @PathVariable int id) throws Exception {
        try {
            return EmpService.updateEmp(emp,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    +++++++++REDIRECTION EXAMPLE++++++++++++++

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
