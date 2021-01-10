package org.launchcode.brewpub.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.launchcode.brewpub.CustomUserDetailService;
import org.launchcode.brewpub.models.data.BrewRepository;
import org.launchcode.brewpub.models.data.PubRepository;
import org.launchcode.brewpub.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BrewRepository brewRepository;

    @MockBean
    private PubRepository pubRepository;

    //    @WithMockUser(value = "user")
    @Test
    public void getCreateAccountShouldReturnTitleCreateAccount() throws Exception {
        this.mockMvc.perform(get("/createAccount")).andExpect(status().isOk()).andExpect(content().string(containsString("createAccount")));
    }


//    @Test
//    public void postCreateAccountShouldReturnTitleCreateAccount() throws Exception {
//
//    }

}
