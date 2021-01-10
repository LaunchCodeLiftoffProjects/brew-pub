package org.launchcode.brewpub;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.launchcode.brewpub.controllers.HomeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrewPubApplicationTests {

	@Autowired
	private HomeController homeController;

	@Test
	void contextLoads() {
	}

	@Test
	void homeControllerLoads() {
		assertThat(homeController).isNotNull();
	}

}
