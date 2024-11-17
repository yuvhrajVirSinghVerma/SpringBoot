package com.mvc.mvcPractice.dto;

public class ResponseEmployeeDto {

    String Employee_Name;
    int Employee_Age;
    int Employee_Id;
    String Employee_Email;


    public int getEmployee_Age() {
        return Employee_Age;
    }

    public void setEmployee_Age(int employee_Age) {
        Employee_Age = employee_Age;
    }

    public String getEmployee_Email() {
        return Employee_Email;
    }

    public void setEmployee_Email(String employee_Email) {
        Employee_Email = employee_Email;
    }

    public int getEmployee_Id() {
        return Employee_Id;
    }

    public void setEmployee_Id(int employee_Id) {
        Employee_Id = employee_Id;
    }

    public String getEmployee_Name() {
        return Employee_Name;
    }

    public void setEmployee_Name(String employee_Name) {
        Employee_Name = employee_Name;
    }
}
