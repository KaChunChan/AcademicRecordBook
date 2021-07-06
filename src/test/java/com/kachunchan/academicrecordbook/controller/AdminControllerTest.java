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
    public void givenAdminUserCreatesAValidNewUser_whenAPostRequestIsHandledSuccessfully_thenReturnAdminPage() throws Exception {
        RequestBuilder request = post("/admin-add-user")
                .with(csrf())
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMINISTRATOR.toString());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin.jsp"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account" , hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account" , hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account" , hasProperty("username", is("username"))))
                .andExpect(model().attribute("account" , hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("account" , hasProperty("password", is("password"))))
                .andExpect(model().attribute("account" , hasProperty("role", is(Role.ADMINISTRATOR))));
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
                .andExpect(model().attribute("account" , hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account" , hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account" , hasProperty("username", is("username"))))
                .andExpect(model().attribute("account" , hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("account" , hasProperty("password", is("password"))))
                .andExpect(model().attribute("account" , hasProperty("role", is(Role.ADMINISTRATOR))));
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
                .andExpect(model().attribute("account" , hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account" , hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account" , hasProperty("username", is(""))))
                .andExpect(model().attribute("account" , hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("account" , hasProperty("password", is("password"))))
                .andExpect(model().attribute("account" , hasProperty("role", is(Role.ADMINISTRATOR))));
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
                .andExpect(model().attribute("account" , hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("account" , hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("account" , hasProperty("username", is("username"))))
                .andExpect(model().attribute("account" , hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("account" , hasProperty("password", is(""))))
                .andExpect(model().attribute("account" , hasProperty("role", is(Role.ADMINISTRATOR))));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenAdminUserCreatesANewUser_whenSelectingRole_thenASelectionOfRolesIsProvidedInTheModel() throws Exception {
        RequestBuilder request = get("/admin-add-user").with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"))
                .andExpect(model().attributeExists("availableRoles"))
                .andExpect(model().attribute("availableRoles", hasItem(Role.ADMINISTRATOR)))
                .andExpect(model().attribute("availableRoles", hasItem(Role.INSTRUCTOR)))
                .andExpect(model().attribute("availableRoles", hasItem(Role.STUDENT)));
    }
}
