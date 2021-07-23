package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Admin;
import com.kachunchan.academicrecordbook.domain.EndUser;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.service.EndUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EndUserService endUserService;
    @MockBean
    private UserDetailsService userDetailsService;
    @Value("${spring.mvc.view.prefix}")
    private String prefixView;

    //Test showAccountView()

    @Test
    @WithMockUser("username")
    @AutoConfigureMockMvc(addFilters = false)
    public void givenAlreadyLoggedInEndUser_whenRequestingAccountPage_thenReturnAccountViewWithRetrievedEndUserDetails() throws Exception {
        RequestBuilder request = get("/account");

        EndUser stubbedEndUser = new Admin(1L, "forename", "surname", "username", "email@email.com", "password");
        when(endUserService.getEndUser(anyString())).thenReturn(stubbedEndUser);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "account.jsp"))
                .andExpect(view().name("account"))
                .andExpect(model().attribute("endUser", hasProperty("id", is(1L))))
                .andExpect(model().attribute("endUser", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("endUser", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("endUser", hasProperty("username", is("username"))))
                .andExpect(model().attribute("endUser", hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("endUser", hasProperty("password", is("password"))))
                .andExpect(model().attribute("endUser", hasProperty("role", is(Role.ADMIN))));
        verify(endUserService).getEndUser("username");
    }

    //Test handleSessionAuthenticationException()

    @Test
    public void givenNonLoggedInEndUser_whenManuallyEnteringAccountPage_thenRedirectToLoginPage() throws Exception {
        RequestBuilder request = get("/account");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    //Test redirectToAccountPage()

    @Test
    @WithMockUser("username")
    public void givenAlreadyLoggedInEndUser_whenDirectedToAccountPage_thenRedirectToAccountPage() throws Exception {
        RequestBuilder request = get("/to-account");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account"));
    }
}