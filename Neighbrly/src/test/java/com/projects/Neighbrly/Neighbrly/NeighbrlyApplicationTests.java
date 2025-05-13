package com.projects.Neighbrly.Neighbrly;

import com.projects.Neighbrly.Neighbrly.entity.User;
import com.projects.Neighbrly.Neighbrly.entity.enums.Role;
import com.projects.Neighbrly.Neighbrly.security.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class NeighbrlyApplicationTests {

	@Autowired
	private JWTService jwtService;
	@Test
	void contextLoads() {

	}

}
