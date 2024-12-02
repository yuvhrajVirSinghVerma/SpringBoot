package com.mvc.mvcPractice;

import com.mvc.mvcPractice.entities.JpaTestEntity;
import com.mvc.mvcPractice.repositories.JpaTestRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SpringBootTest
class MvcPracticeApplicationTests {

@Autowired
JpaTestRepo jparepo;
	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		List<JpaTestEntity>l=jparepo.getByfirmName("google");
		System.out.println(l);
	}



}
