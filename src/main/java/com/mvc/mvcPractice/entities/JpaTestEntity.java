package com.mvc.mvcPractice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "yuvi",schema = "TestEntity1")
@AllArgsConstructor
@NoArgsConstructor
public class JpaTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
   private String name;

    @Column(name = "firmName")
   private String firmName;
}
