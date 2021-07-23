package com.kachunchan.academicrecordbook.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SecurityController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsService userDetailsService;
    @Value("${spring.mvc.view.prefix}")
    private String prefixView;

    //Test login

    @Test
    public void givenEndUserAccessingLoginPage_whenRequestingLoginPage_thenReturnLoginViewWithNoParameters() throws Exception {
        RequestBuilder request = get("/login");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView.toString() + "login.jsp"))
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeDoesNotExist("logout"));
    }

    @Test
    public void givenEndUserHasPreviousUnsuccessfulLoginAttempts_whenRequestingLoginPage_thenReturnLoginViewWithErrorParameter() throws Exception {
        RequestBuilder request = get("/login?error=true");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView.toString() + "login.jsp"))
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "true"))
                .andExpect(model().attributeDoesNotExist("logout"));
    }

    //Test logout

    @Test
    public void givenEndUserHasSuccessfulLoggedOut_whenRequestingLoginPage_thenReturnLoginViewWithLogoutParameter() throws Exception {
        RequestBuilder request = get("/login?logout=true");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView.toString() + "login.jsp"))
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attribute("logout", "true"));
    }

    @Test
    public void givenEndUserManuallyTypeInNonExistingParameters_whenRequestingLoginPage_thenReturnError302() throws Exception {
        RequestBuilder request = get("/login?hello=world");

        mockMvc.perform(request)
                .andExpect(status().isFound());
    }

    @Test
    public void givenManuallyTypeInUnexpectedValueToExistingParameters_whenRequestingLoginPage_thenReturnError302() throws Exception {
        RequestBuilder request = get("/login?error=helloworld");

        mockMvc.perform(request)
                .andExpect(status().isFound());
    }
}
