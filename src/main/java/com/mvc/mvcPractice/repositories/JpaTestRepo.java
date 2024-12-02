package com.mvc.mvcPractice.repositories;

import com.mvc.mvcPractice.entities.JpaTestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTestRepo extends JpaRepository<JpaTestEntity,Integer> {
    List<JpaTestEntity> getByfirmName(String firmName);
    List<JpaTestEntity> findByOrderByPrice();
    List<JpaTestEntity> findByOrderByName();
    List<JpaTestEntity> findBy(Sort sortby);



}
