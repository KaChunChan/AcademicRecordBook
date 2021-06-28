package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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
    private AccountServiceImpl accountService;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser("username")
    @AutoConfigureMockMvc(addFilters = false)
    public void givenAlreadyLoggedInUser_whenGoingToAccountPageUsingGet_thenReturnAccountDetails() throws Exception {
        Account stubAccount = new Account(1L, "forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        when(accountService.getAnAccount(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(stubAccount);
        mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("account"))
                .andExpect(forwardedUrl("/WEB-INF/view/account.jsp"))
                .andExpect(model().attribute("account", hasProperty("id", is(1L))))
                .andExpect(model().attribute("account", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account", hasProperty("username", is("username"))))
                .andExpect(model().attribute("account", hasProperty("email", is("email"))))
                .andExpect(model().attribute("account", hasProperty("password", is("password"))))
                .andExpect(model().attribute("account", hasProperty("role", is(Role.ADMINISTRATOR))));
    }

    @Test
    public void givenNotLoggedInUser_whenManuallyEnteringAccountPageUsingGet_thenRedirectToLoginPage() throws Exception {
        mockMvc.perform(get("/account"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}