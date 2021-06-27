package com.kachunchan.academicrecordbook.account.controller;

import com.kachunchan.academicrecordbook.account.domain.Account;
import com.kachunchan.academicrecordbook.account.domain.Role;
import com.kachunchan.academicrecordbook.account.exception.AccountDoesNotExistException;
import com.kachunchan.academicrecordbook.account.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @PreAuthorize("authenticated")
    public void whenGettingAccountAndAccountExist_ThenReturnAccount() throws Exception {
        Account stubAccount = new Account(1L, "forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        when(accountService.getAnAccount(anyString())).thenReturn(stubAccount);

        mockMvc.perform(get("/{username}/account", "username"))
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
    public void whenGettingAccountAndAccountDoesNotExist_ThenThrowException() throws Exception {
        when(accountService.getAnAccount(anyString())).thenThrow(new AccountDoesNotExistException());

        mockMvc.perform(get("/{username}", "username"))
                .andExpect(status().isNotFound());
    }
}
