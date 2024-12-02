package com.mvc.mvcPractice.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "yuvi2",schema = "TestEntity2")
public class TestEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String Name;
    String FirmName;
}
