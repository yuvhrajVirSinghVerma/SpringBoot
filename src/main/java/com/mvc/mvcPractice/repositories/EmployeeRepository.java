package com.mvc.mvcPractice.repositories;

import com.mvc.mvcPractice.dto.RequestEmployeeDto;
import com.mvc.mvcPractice.dto.ResponseEmployeeDto;
import com.mvc.mvcPractice.entities.EmployeeEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    List<EmployeeEntity>AllEmps=new ArrayList<>();
    public EmployeeEntity getEmpfromDb(RequestEmployeeDto emp){
        //fetch from db
        EmployeeEntity e=executeQuery(emp);
        return e;
    }

    public EmployeeEntity CreateEmp(EmployeeEntity emp){
        AllEmps.add(emp);
        System.out.println(AllEmps.toString());
        return emp;
    }

    public List<EmployeeEntity> getAllEmps(){
        return AllEmps;
    }

    public EmployeeEntity executeQuery(RequestEmployeeDto emp){

        //mapping db columns to our entity fields and send them as response
        EmployeeEntity e=new EmployeeEntity();
        e.setEmpAge(emp.getAge());
        e.setEmpEmail(emp.getEmail());
        e.setEmpId(emp.getId());
        e.setEmpName(emp.getName());
        return  e;
    }

    public EmployeeEntity findById(int id) {
        for(EmployeeEntity r:AllEmps){
            if(r.getEmpId()==id){
                System.out.println("id matched");
                return r;
            }
        }
        return  null;
    }

    public boolean save(EmployeeEntity emp) {
        int ind=0;
        for(EmployeeEntity r:AllEmps){
            if(r.getEmpId()==emp.getEmpId()){
                AllEmps.set(ind,emp);
                return true;
            }
            ind++;
        }
        return false;
    }
}
