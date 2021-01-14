package org.launchcode.brewpub.webAppTest;

import org.junit.jupiter.api.Test;
import org.launchcode.brewpub.models.data.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTest {

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
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(containsString("Welcome!")));
    }
}
