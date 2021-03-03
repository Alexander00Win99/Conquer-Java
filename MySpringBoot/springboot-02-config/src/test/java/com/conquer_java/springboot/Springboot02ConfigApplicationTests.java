package com.conquer_java.springboot;

import com.conquer_java.springboot.pojo.Cat;
import com.conquer_java.springboot.pojo.Dog;
import com.conquer_java.springboot.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot02ConfigApplicationTests {
	@Autowired
	private Person person;
	@Autowired
	private Dog dog;
	@Autowired
	private Cat cat;

	@Test
	void contextLoads() {
		System.out.println(person);
		System.out.println(dog);
		dog.skill();
		System.out.println(cat);
		cat.skill();
	}

}
