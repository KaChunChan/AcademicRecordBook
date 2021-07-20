package com.kachunchan.academicrecordbook.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(addFilters = false)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsService userDetailsService;

    //Test redirectToLogin

    @Test
    public void givenNoLoggedInEndUser_whenRequestingHomePage_thenRedirectToLoginPage() throws Exception {
        RequestBuilder request = get("/academicrecordbook");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void givenNoLoggedInEndUser_whenEnteredJustForwardSlashInURL_thenRedirectToLoginPage() throws Exception {
        RequestBuilder request = get("/");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser("username")
    public void givenAlreadyLoggedInEndUser_whenRequestingHomePage_thenRedirectToAccountPage() throws Exception {
        RequestBuilder request = get("/academicrecordbook");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account"));
    }

    @Test
    @WithMockUser("username")
    public void givenAlreadyLoggedInEndUser_whenEnteredJustForwardSlashInURL_thenRedirectToLoginPage() throws Exception {
        RequestBuilder request = get("/");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account"));
    }
}
