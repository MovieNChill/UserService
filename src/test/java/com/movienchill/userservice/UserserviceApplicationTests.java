package com.movienchill.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.movienchill.userservice.model.User;

@SpringBootTest
class UserserviceApplicationTests {

	@Test
	void testUser() {
		User user = new User();
		user.setPseudo("test");
	}
}