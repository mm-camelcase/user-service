package com.mm.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;

import com.mm.user.config.TestSecurityConfig;

@SpringBootTest
//@ActiveProfiles("test")	// A profile is used where security is disabled
@Import(TestSecurityConfig.class)
class UserApplicationTests {

	@Test
	void contextLoads() {
	}

}
