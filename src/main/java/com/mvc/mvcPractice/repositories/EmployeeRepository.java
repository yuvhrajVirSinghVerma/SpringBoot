package com.mvc.mvcPractice.repositories;

import com.mvc.mvcPractice.dto.RequestEmployeeDto;
import com.mvc.mvcPractice.dto.ResponseEmployeeDto;
import com.mvc.mvcPractice.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    @Autowired
    JdbcTemplate jdbc;

    List<EmployeeEntity>AllEmps=new ArrayList<>();
    public void createTable(){
        jdbc.execute("CREATE TABLE emps (empId INT AUTO_INCREMENT PRIMARY KEY ,"+"empName VARCHAR(100), age INT)");
    }
    public EmployeeEntity getEmpfromDb(RequestEmployeeDto emp){
        //fetch from db
        EmployeeEntity e=executeQuery(emp);
        return e;
    }

    public EmployeeEntity CreateEmp(EmployeeEntity emp){
        AllEmps.add(emp);
        System.out.println(AllEmps.toString());
        String checkTableExistsQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'PUBLIC' AND table_name = 'EMPS'";
        int count = jdbc.queryForObject(checkTableExistsQuery, Integer.class);
        System.out.println(count);
        if(count==0)createTable();

        String query="INSERT INTO emps (empName, age) VALUES(?, ?)";
        jdbc.update(query,emp.getEmpName(),emp.getEmpAge());
        return emp;
    }

    public List<EmployeeEntity> getAllEmps(){
       List<EmployeeEntity>AllEmpsList=jdbc.query("SELECT * FROM emps",(rs,col)->{
           EmployeeEntity emp=new EmployeeEntity();
           emp.setEmpName(rs.getString("empName"));
           emp.setEmpAge(rs.getInt("age"));
           emp.setEmpId(rs.getInt("empId"));
           return emp;
       });
        return AllEmpsList;
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

        System.out.println(id);
        String str="select * from emps where empid= ?";
List<EmployeeEntity>empl=jdbc.query(
        str,
        new Object[]{id},
        (rs, rowNum) -> {
            EmployeeEntity employee = new EmployeeEntity();
            employee.setEmpAge(rs.getInt("age"));
            employee.setEmpName(rs.getString("empName"));
            employee.setEmpId(rs.getInt("empId"));
            employee.setEmpEmail("tempEmail");//no column of email in db
            return employee;
        }
);
        return empl.size()>0? empl.get(0):null;
//        for(EmployeeEntity r:AllEmps){
//            if(r.getEmpId()==id){
//                System.out.println("id matched");
//                return r;
//            }
//        }
//        return  null;
    }

    public boolean save(EmployeeEntity emp) {
        int ind=0;
        String updateSQL = "UPDATE emps SET empName = ? , age= ? WHERE empId = ?";
int cnt=jdbc.update(updateSQL,emp.getEmpName(),emp.getEmpAge(),emp.getEmpId());
        System.out.println("updatecnt "+cnt);
//        for(EmployeeEntity r:AllEmps){
//            if(r.getEmpId()==emp.getEmpId()){
//                AllEmps.set(ind,emp);
//                return true;
//            }
//            ind++;
//        }
        return true;
    }
}
