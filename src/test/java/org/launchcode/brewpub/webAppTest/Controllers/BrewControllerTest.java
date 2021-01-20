package org.launchcode.brewpub.webAppTest.Controllers;

import org.junit.jupiter.api.Test;
import org.launchcode.brewpub.models.data.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BrewControllerTest {

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
    @WithMockUser("normalUser")
    public void shouldRespondWith2xx() throws Exception {
        this.mockMvc.perform(get("/pubs/brews/view/12")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser("normalUser")
    public void shouldRespondWithBrewAndPub() throws Exception {
        this.mockMvc.perform(get("/pubs/brews/view/4")).andExpect(status().isOk());
    }

}
