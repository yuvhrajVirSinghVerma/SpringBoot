package com.mvc.mvcPractice.service;

import com.mvc.mvcPractice.ModleMapperConfig.ModelMapperConfig;
import com.mvc.mvcPractice.dto.RequestEmployeeDto;
import com.mvc.mvcPractice.dto.ResponseEmployeeDto;
import com.mvc.mvcPractice.entities.EmployeeEntity;
import com.mvc.mvcPractice.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empRepo;

    @Autowired
    ModelMapper mapper;

    public  List<ResponseEmployeeDto> getAllEmps() {
        List<EmployeeEntity>AllEmps=empRepo.getAllEmps();
         return AllEmps
                .stream()
                .map(val->{


                    TypeMap<EmployeeEntity, ResponseEmployeeDto> typeMap=mapper.getTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);
         if(typeMap==null)typeMap = mapper.createTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);

            typeMap.addMappings(mapper -> {
                mapper.map(EmployeeEntity::getEmpName, ResponseEmployeeDto::setEmployee_Name);
                mapper.map(EmployeeEntity::getEmpId, ResponseEmployeeDto::setEmployee_Id);
                mapper.map(EmployeeEntity::getEmpAge, ResponseEmployeeDto::setEmployee_Age);
                mapper.map(EmployeeEntity::getEmpEmail, ResponseEmployeeDto::setEmployee_Email);
            });
            return mapper.map(val, ResponseEmployeeDto.class);
        }).collect(Collectors.toCollection(ArrayList::new));

    }

    public ResponseEmployeeDto getEmpData(RequestEmployeeDto emp){
        //sending dto to repository
        EmployeeEntity employee= empRepo.getEmpfromDb(emp);

        //process logic

        //mapping entity to responseDto

        TypeMap<EmployeeEntity, ResponseEmployeeDto> typeMap=mapper.getTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);
        if(typeMap==null)typeMap = mapper.createTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);

        typeMap.addMappings(mapper -> {
            mapper.map(EmployeeEntity::getEmpName, ResponseEmployeeDto::setEmployee_Name);
            mapper.map(EmployeeEntity::getEmpId, ResponseEmployeeDto::setEmployee_Id);
            mapper.map(EmployeeEntity::getEmpAge, ResponseEmployeeDto::setEmployee_Age);
            mapper.map(EmployeeEntity::getEmpEmail, ResponseEmployeeDto::setEmployee_Email);

        });

        return mapper.map(employee, ResponseEmployeeDto.class);
//        ResponseEmployeeDto resp=new ResponseEmployeeDto();
//        resp.setEmployee_Name(employee.getEmpName());
//        resp.setEmployee_Id(employee.getEmpId());
//        resp.setEmployee_Age(employee.getEmpAge());
//        resp.setEmployee_Email(employee.getEmpEmail());
//        return resp;
    }

    public Optional<ResponseEmployeeDto> CreateEmp(RequestEmployeeDto emp){
        TypeMap<RequestEmployeeDto, EmployeeEntity> typeMap=mapper.getTypeMap(RequestEmployeeDto.class, EmployeeEntity.class);
        if(typeMap==null)typeMap = mapper.createTypeMap(RequestEmployeeDto.class, EmployeeEntity.class);

        typeMap.addMappings(mapper -> {
            mapper.map(RequestEmployeeDto::getName, EmployeeEntity::setEmpName);
            mapper.map(RequestEmployeeDto::getId, EmployeeEntity::setEmpId);
            mapper.map(RequestEmployeeDto::getAge, EmployeeEntity::setEmpAge);
            mapper.map(RequestEmployeeDto::getEmail, EmployeeEntity::setEmpEmail);
        });

         EmployeeEntity empCreatedRes=empRepo.CreateEmp(mapper.map(emp, EmployeeEntity.class));

        TypeMap<EmployeeEntity, ResponseEmployeeDto> typeMap2=mapper.getTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);
        if(typeMap2==null)typeMap2 = mapper.createTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);

        typeMap2.addMappings(mapper -> {
            mapper.map(EmployeeEntity::getEmpName, ResponseEmployeeDto::setEmployee_Name);
            mapper.map(EmployeeEntity::getEmpId, ResponseEmployeeDto::setEmployee_Id);
            mapper.map(EmployeeEntity::getEmpAge, ResponseEmployeeDto::setEmployee_Age);
            mapper.map(EmployeeEntity::getEmpEmail, ResponseEmployeeDto::setEmployee_Email);
        });

        return Optional.ofNullable(mapper.map(empCreatedRes, ResponseEmployeeDto.class));
    }

    public ResponseEmployeeDto updateEmp(RequestEmployeeDto empdto, int id) throws Exception {
        EmployeeEntity emp=empRepo.findById(id);
        System.out.println("emp found "+emp);
        if(emp==null)return  null;



        for(Field field:empdto.getClass().getDeclaredFields()){
            System.out.println("field access "+field.toString());

            field.setAccessible(true);
            System.out.println("field access true++++"+field.toString());

            String s[]=String.valueOf(field).split("\\.");
            System.out.println("String.valueOf(field).split(\".\") "+ Arrays.toString(s));
            String str=s[s.length-1];
            Field f=ReflectionUtils.findField(emp.getClass(), "Emp"+Character.toUpperCase(str.charAt(0))+str.substring(1));
            System.out.println("field founded true++++ "+String.valueOf(f)+" ====> "+String.valueOf(field));


            System.out.println("hellllllllloooooo "+("Emp"+Character.toUpperCase(str.charAt(0))+str.substring(1)));


            if(f!=null) {
                try {
                    f.setAccessible(true);
                    ReflectionUtils.setField(f,emp,field.get(empdto));
                    System.out.println("field setted "+f.toString());

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        TypeMap<EmployeeEntity, ResponseEmployeeDto> typeMap=mapper.getTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);
        if(typeMap==null)typeMap = mapper.createTypeMap(EmployeeEntity.class, ResponseEmployeeDto.class);

        typeMap.addMappings(mapper -> {
            mapper.map(EmployeeEntity::getEmpName, ResponseEmployeeDto::setEmployee_Name);
            mapper.map(EmployeeEntity::getEmpId, ResponseEmployeeDto::setEmployee_Id);
            mapper.map(EmployeeEntity::getEmpAge, ResponseEmployeeDto::setEmployee_Age);
            mapper.map(EmployeeEntity::getEmpEmail, ResponseEmployeeDto::setEmployee_Email);
        });
        if(!empRepo.save(emp))throw new Exception("user not updated");
        System.out.println("save user");
        return mapper.map(emp, ResponseEmployeeDto.class);


    }
}
