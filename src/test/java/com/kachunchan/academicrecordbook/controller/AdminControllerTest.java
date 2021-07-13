package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.service.AccountFormService;
import com.kachunchan.academicrecordbook.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountFormService accountFormService;

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserRequestingAdminPage_whenAGetRequestIsHandled_thenReturnAdminPage() throws Exception {
        RequestBuilder request = get("/admin")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin.jsp"));
    }

    @Test
    @WithMockUser(roles = "S")
    public void givenNonAdminUserRequestingAdminPage_whenAGetRequestIsHandled_thenShowForbiddenError() throws Exception {
        RequestBuilder request = get("/admin")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserRequestingAddNewUser_whenAGetRequestIsHandled_thenReturnAdminAddUserPage() throws Exception {
        RequestBuilder request = get("/admin-add-user")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserCreatesAValidNewUser_whenAPostRequestIsHandledSuccessfully_thenRedirectToAdminPage() throws Exception {
        RequestBuilder request = post("/admin-add-user")
                .with(csrf())
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMINISTRATOR.toString());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).getAnAccount(anyString());
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserCreatesANewUser_whenTheUsernameAlreadyExists_thenReturnBackToAdminAddUserPageWithSameFormEntry() throws Exception {
        RequestBuilder request = post("/admin-add-user")
                .with(csrf())
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMINISTRATOR.toString());

        when(accountService.getAnAccount(anyString())).thenReturn(new Account());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account", hasProperty("username", is("username"))))
                .andExpect(model().attribute("account", hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("account", hasProperty("password", is("password"))))
                .andExpect(model().attribute("account", hasProperty("role", is(Role.ADMINISTRATOR))));
        verify(accountService).getAnAccount(anyString());
        verifyNoMoreInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserCreatesANewUser_whenTheUsernameIsBlank_thenReturnBackToAdminAddUserPageWithSameFormEntry() throws Exception {
        RequestBuilder request = post("/admin-add-user")
                .with(csrf())
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMINISTRATOR.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account", hasProperty("username", is(""))))
                .andExpect(model().attribute("account", hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("account", hasProperty("password", is("password"))))
                .andExpect(model().attribute("account", hasProperty("role", is(Role.ADMINISTRATOR))));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserCreatesANewUser_whenThePasswordIsBlank_thenReturnBackToAdminAddUserPageWithSameFormEntry() throws Exception {
        RequestBuilder request = post("/admin-add-user")
                .with(csrf())
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "")
                .param("role", Role.ADMINISTRATOR.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account", hasProperty("username", is("username"))))
                .andExpect(model().attribute("account", hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("account", hasProperty("password", is(""))))
                .andExpect(model().attribute("account", hasProperty("role", is(Role.ADMINISTRATOR))));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserCreatesANewUser_whenSelectingRole_thenASelectionOfRolesIsProvidedInTheModel() throws Exception {
        RequestBuilder request = get("/admin-add-user")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"))
                .andExpect(model().attributeExists("availableRoles"))
                .andExpect(model().attribute("availableRoles", hasItem(Role.ADMINISTRATOR)))
                .andExpect(model().attribute("availableRoles", hasItem(Role.INSTRUCTOR)))
                .andExpect(model().attribute("availableRoles", hasItem(Role.STUDENT)));
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserRequestAdminPage_whenRetrievingPage_thenDisplayAllUserAccounts() throws Exception {
        RequestBuilder request = get("/admin")
                .with(csrf());

        Account account1 = new Account(1L, "forename1", "surname1", "username1", "email@eamil.com1", "password1", Role.ADMINISTRATOR);
        Account account2 = new Account(2L,"forename2", "surname2", "username2", "email@eamil.com2", "password2", Role.INSTRUCTOR);
        Account account3 = new Account(3L, "forename3", "surname3", "username3", "email@eamil.com3", "password3", Role.STUDENT);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin.jsp"))
                .andExpect(model().attribute("accounts", hasItem(account1)))
                .andExpect(model().attribute("accounts", hasItem(account2)))
                .andExpect(model().attribute("accounts", hasItem(account3)));
        verify(accountService).getAllAccounts();
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminViewingAListOfUsersAccounts_whenAnAccountHasBeenRequestedToBeDeleted_thenDeleteUserAccountAndRedirectToAdminPage() throws Exception {
        RequestBuilder request = post("/admin-delete-user")
                .with(csrf())
                .param("accountID", "2");

        Account stubbedAccount = new Account(1L, "forename1", "surname1", "username1", "email@eamil.com1", "password1", Role.ADMINISTRATOR);
        when(accountService.getAnAccount(anyString())).thenReturn(stubbedAccount);

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).getAnAccount(any());
        verify(accountService).deleteAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminViewingAListOfUsersAccounts_whenAdminDeletesTheirAccount_thenDoNotDeleteAccountAndRedirectToAdminPageWithErrorMessage() throws Exception {
        RequestBuilder request = post("/admin-delete-user")
                .with(csrf())
                .param("accountID", "1");

        Account stubbedAccount = new Account(1L, "forename1", "surname1", "username1", "email@eamil.com1", "password1", Role.ADMINISTRATOR);
        when(accountService.getAnAccount(anyString())).thenReturn(stubbedAccount);

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"))
        .andExpect(flash().attribute("error", is("Cannot delete current user account")));
        verify(accountService).getAnAccount(any());
        verifyNoMoreInteractions(accountService);
    }
}
