package org.launchcode.brewpub.webAppTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.launchcode.brewpub.controllers.HomeController;
import org.launchcode.brewpub.models.data.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class BrewPubApplicationTests {

	@Autowired
	private HomeController homeController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BrewRepository brewRepository;

	@Mock
	private PubRepository pubRepository;

	@Mock
	private PubReviewRepository pubReviewRepository;

	@Mock
	private BrewReviewRepository brewReviewRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void homeControllerLoads() {
		assertThat(homeController).isNotNull();
	}

}
