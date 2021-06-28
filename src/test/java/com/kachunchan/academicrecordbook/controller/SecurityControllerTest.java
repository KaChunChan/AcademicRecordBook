package com.kachunchan.academicrecordbook.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SecurityController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void givenNoPreviousLoginAttempt_whenRedirectedToLogin_thenReturnLoginPageWithNoParameters() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/login.jsp"))
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeDoesNotExist("logout"));
    }

    @Test
    public void givenPreviousUnsuccessfulLoginAttempt_whenRedirectToLogin_thenReturnLoginPageWithErrorParameter() throws Exception {
        mockMvc.perform(get("/login?error=true"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/login.jsp"))
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "true"))
                .andExpect(model().attributeDoesNotExist("logout"));
    }

    @Test
    public void givenPreviouslySuccessfullyLogout_whenRedirectToLogin_thenReturnLoginPageWithLogoutParameter() throws Exception {
        mockMvc.perform(get("/login?logout=true"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/login.jsp"))
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attribute("logout", "true"));
    }

    @Test
    public void givenManuallyTypeInNonExistingParameters_whenRequestingLogin_thenReturnError302() throws Exception {
        mockMvc.perform(get("/login?hello=world"))
                .andExpect(status().isFound());
    }

    @Test
    public void givenManuallyTypeInUnexpectedValueToExistingParameters_whenRequestingLogin_thenReturnError302() throws Exception {
        mockMvc.perform(get("/login?error=helloworld"))
                .andExpect(status().isFound());
    }
}
