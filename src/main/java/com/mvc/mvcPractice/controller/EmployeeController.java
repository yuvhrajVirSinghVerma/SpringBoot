package com.mvc.mvcPractice.controller;

import com.mvc.mvcPractice.CustomValidator.groups.groupA;
import com.mvc.mvcPractice.advices.ApiResponse;
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

import java.sql.*;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService EmpService;

    @GetMapping(path = "/hello")
    public ResponseEntity getHandler(){
        ResultSetCloseConnectionDemo();
        return new ResponseEntity<>(new ApiResponse<>(null,"hello"),HttpStatus.ACCEPTED);
    }

    private void ResultSetCloseConnectionDemo() {
                String url = "jdbc:h2:mem:testdb";
        String username = "sa";

        // Declare connection, statement, and result set
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username, "");
            System.out.println("Database connection established.");

            // Create a statement object to execute a query
            statement = connection.createStatement();

            // Execute a query to get data
            String query = "SELECT empId, empName FROM emps";
            resultSet = statement.executeQuery(query);

            // Read data from the ResultSet
            while (resultSet.next()) {
                int id = resultSet.getInt("empId");
                String name = resultSet.getString("empName");
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // Close the connection (this will close the ResultSet too)
//            connection.close();
            System.out.println("Database connection closed.");

            // Now let's try to access the ResultSet after closing the connection
            try {
                // Trying to access the resultSet after the connection is closed
                resultSet.next();  // This will throw an exception
            } catch (SQLException e) {
                System.out.println("Exception occurred: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources (if still open)
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping(path = "/employees/{id}")
    public ResponseEmployeeDto getEmp(@PathVariable(name = "id") int empId){
        return EmpService.getEmpData(empId);
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
