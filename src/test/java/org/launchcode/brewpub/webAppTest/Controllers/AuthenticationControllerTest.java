package org.launchcode.brewpub.webAppTest.Controllers;

import org.junit.jupiter.api.Test;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.launchcode.brewpub.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BrewRepository brewRepository;

    @MockBean
    private PubRepository pubRepository;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(containsString("Welcome")));
    }

    @Test
    @WithMockUser("normalUser")
    public void shouldShowUserLoggedIn() throws Exception {
        this.mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(content().string(containsString("normalUser")));
    }

    @Test
    @WithMockUser("anotherUser")
    public void logoutShouldRedirectToLogin() throws Exception {
        this.mockMvc.perform(get("/logout")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    public void unauthorizedRequestShouldRedirectToLogin() throws Exception {
        this.mockMvc.perform(get("/pubs/")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("http://localhost/login"));
    }
}
