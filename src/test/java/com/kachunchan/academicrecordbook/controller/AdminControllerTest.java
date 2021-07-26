package com.kachunchan.academicrecordbook.controller;

import com.kachunchan.academicrecordbook.domain.*;
import com.kachunchan.academicrecordbook.service.EndUserFormService;
import com.kachunchan.academicrecordbook.service.EndUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import static org.mockito.ArgumentMatchers.*;
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
    private EndUserService endUserService;
    @MockBean
    private EndUserFormService endUserFormService;
    @Value("${spring.mvc.view.prefix}")
    private String prefixView;

    //Test getAdminPage()

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUser_whenRequestingAdminPage_thenReturnAdminView() throws Exception {
        RequestBuilder request = get("/admin")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin.jsp"))
                .andExpect(view().name("admin"));
    }

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUser_whenRequestingAdminPage_thenReturnAdminViewWithListOfEndUsers() throws Exception {
        RequestBuilder request = get("/admin")
                .with(csrf());

        EndUser endUser1 = new Admin(1L, "forename1", "surname1", "username1", "email@eamil.com1", "password1");
        EndUser endUser2 = new Instructor(2L, "forename2", "surname2", "username2", "email@eamil.com2", "password2");
        EndUser endUser3 = new Student(3L, "forename3", "surname3", "username3", "email@eamil.com3", "password3");
        List<EndUser> endUsers = new ArrayList<>();
        endUsers.add(endUser1);
        endUsers.add(endUser2);
        endUsers.add(endUser3);
        when(endUserService.getAllEndUsers()).thenReturn(endUsers);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin.jsp"))
                .andExpect(view().name("admin"))
                .andExpect(model().attribute("endUsers", hasItem(endUser1)))
                .andExpect(model().attribute("endUsers", hasItem(endUser2)))
                .andExpect(model().attribute("endUsers", hasItem(endUser3)));
        verify(endUserService).getAllEndUsers();
    }

    @Test
    @WithMockUser(roles = "S")
    public void givenNonAdminEndUser_whenRequestingAdminPage_thenShowForbiddenError() throws Exception {
        RequestBuilder request = get("/admin")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    //Test getAdminAddUserPage()

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUser_whenRequestingAdminAddUserPage_thenReturnAdminAddUserViewWithAListOfRolesInTheModel() throws Exception {
        RequestBuilder request = get("/admin-add-user")
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-account-form.jsp"))
                .andExpect(view().name("admin-account-form"))
                .andExpect(model().attributeExists("availableRoles"))
                .andExpect(model().attribute("availableRoles", hasItem(Role.ADMIN)))
                .andExpect(model().attribute("availableRoles", hasItem(Role.INSTRUCTOR)))
                .andExpect(model().attribute("availableRoles", hasItem(Role.STUDENT)));
    }

    //Test addNewUser

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserEnteredValidNewEndUserDetails_whenRequestingAddNewUser_thenConvertFormAndAddNewEndUserAndRedirectToAdminPage() throws Exception {
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMIN.toString())
                .with(csrf());

        EndUser newEndUser = new Admin(1L, "forename", "surname", "username", "email@email.com", "password");
        when(endUserFormService.convertToEndUser(any())).thenReturn(newEndUser);
        when(endUserService.addEndUser(any())).thenReturn(new Admin());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
        verify(endUserFormService).convertToEndUser(any());
        verify(endUserService).addEndUser(any());
    }

    @Test
    @WithMockUser(authorities = "A")
    public void givenAdminEndUserEnteredValidNewEndUserDetailsButUsernameAlreadyExists_whenRequestingAddNewUser_thenReturnBackToAdminAddUserViewWithSameFormEntry() throws Exception {
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMIN.toString())
                .with(csrf());

        when(endUserService.addEndUser(any())).thenReturn(null);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-account-form"))
                .andExpect(model().attribute("endUser", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("endUser", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("endUser", hasProperty("username", is("username"))))
                .andExpect(model().attribute("endUser", hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("endUser", hasProperty("password", is("password"))))
                .andExpect(model().attribute("endUser", hasProperty("role", is(Role.ADMIN))));
        verify(endUserFormService).convertToEndUser(any());
        verify((endUserService)).addEndUser(any());
    }

    //Test deleteUser()

    @Test
    @WithMockUser(authorities = "A", username = "username1")
    public void givenAdminEndUserSelectsAnEndUserToBeDeleted_whenRequestingAdminDeleteUser_thenDeleteUserAccountAndRedirectToAdminPage() throws Exception {
        RequestBuilder request = post("/admin-delete-user")
                .with(csrf())
                .param("endUserID", "2");

        EndUser stubbedAdminAccount = new Admin(1L, "forename1", "surname1", "username1", "email@eamil.com1", "password1");
        EndUser stubbedEndUserToBeDeleted = new Instructor(2L, "forename2", "surname2", "username2", "email@eamil.com2", "password2");
        when(endUserService.getEndUser(anyString())).thenReturn(stubbedAdminAccount);
        when(endUserService.getEndUser(anyLong())).thenReturn(stubbedEndUserToBeDeleted);

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(endUserService).getEndUser("username1");
        verify(endUserService).getEndUser(2L);
        verify(endUserService).deleteEndUser(2L);
    }


    @Test
    @WithMockUser(authorities = "A", username = "username1")
    public void givenAdminEndUserSelectsThemselvesToBeDeleted_whenRequestingAdminDeleteUser_thenDoNotDeleteOwnAccountAndRedirectToAdminPageWithErrorMessage() throws Exception {
        RequestBuilder request = post("/admin-delete-user")
                .with(csrf())
                .param("endUserID", "1");

        EndUser stubbedAdminAccount = new Admin(1L, "forename1", "surname1", "username1", "email@eamil.com1", "password1");
        when(endUserService.getEndUser(anyString())).thenReturn(stubbedAdminAccount);

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"))
                .andExpect(flash().attribute("error", is("Cannot delete current user.")));
        verify(endUserService).getEndUser("username1");
        verifyNoMoreInteractions(endUserService);
    }

    @Test
    @WithMockUser(authorities = "A", username = "username1")
    public void givenAnAdminEndUsersDeletesAnEndUserAndAnotherAdminEndUserDeletesTheSameEndUser_whenRequestingAdminDeleteUser_thenReturnToAdminPageWithUserAlreadyDeletedMessage() throws Exception {
        RequestBuilder request = post("/admin-delete-user")
                .with(csrf())
                .param("endUserID", "2");

        EndUser stubbedAdminEndUser = new Admin(1L, "forename1", "surname1", "username1", "email@eamil.com1", "password1");
        when(endUserService.getEndUser(anyString())).thenReturn(stubbedAdminEndUser);
        when(endUserService.getEndUser(anyLong())).thenReturn(null);

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"))
                .andExpect(flash().attribute("error", is("User does not exist or has already been deleted.")));
        verify(endUserService).getEndUser("username1");
        verify(endUserService).getEndUser(2L);
        verifyNoMoreInteractions(endUserService);
    }

    //Test editUser()

    @Test
    @WithMockUser(authorities = "A")
    public void givenAnAdminEndUserSelectsAnEndUserForTheirDetailsToBeEdited_whenRequestingAdminEditUser_thenReturnAdminAccountFormViewWithExistingEndUserDetails() throws Exception {
        RequestBuilder request = get("/admin-edit-user")
                .param("endUserID", "1")
                .with(csrf());

        EndUser stubbedEndUser = new Admin(1L, "forename1", "surname1", "username1", "email@email.com1", "password1");
        EndUserForm stubbedEndUserForm = new EndUserForm();
        stubbedEndUserForm.setForename("forename1");
        stubbedEndUserForm.setSurname("surname1");
        stubbedEndUserForm.setUsername("username1");
        stubbedEndUserForm.setEmail("email@email.com1");
        stubbedEndUserForm.setPassword("password1");
        stubbedEndUserForm.setRole(Role.ADMIN);
        when(endUserService.getEndUser(anyLong())).thenReturn(stubbedEndUser);
        when(endUserFormService.convertToEndUserForm(any())).thenReturn(stubbedEndUserForm);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-account-form.jsp"))
                .andExpect(view().name("admin-account-form"))
                .andExpect(model().attribute("endUserID", is("1")))
                .andExpect(model().attribute("endUser", hasProperty("forename", is("forename1"))))
                .andExpect(model().attribute("endUser", hasProperty("surname", is("surname1"))))
                .andExpect(model().attribute("endUser", hasProperty("username", is("username1"))))
                .andExpect(model().attribute("endUser", hasProperty("email", is("email@email.com1"))))
                .andExpect(model().attribute("endUser", hasProperty("password", is("password1"))))
                .andExpect(model().attribute("endUser", hasProperty("role", is(Role.ADMIN))));
        verify(endUserService).getEndUser(1L);
    }

    //Test updateUser

    @Test
    @WithMockUser(authorities = "A", username = "username")
    public void givenAnAdminEndUserUpdatingAnEndUserAfterEditingTheirDetailsWithValidValues_whenRequestingAdminEditUser_thenUpdateEndUserDetailsAndRedirectToAdminPage() throws Exception {
        RequestBuilder request = post("/admin-edit-user")
                .param("endUserID", "1")
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMIN.toString())
                .with(csrf());

        EndUser stubbedEndUser = new Admin(1L, "forename", "surname", "username", "email@email.com", "password");
        when(endUserFormService.convertToEndUser(any())).thenReturn(stubbedEndUser);

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(endUserFormService).convertToEndUser(any());
        verify(endUserService).editEndUser(stubbedEndUser);
    }

    @Test
    @WithMockUser(authorities = "A", username = "username")
    public void givenAnAdminEndUserUpdatingAnEndUserWithInvalidDetails_whenRequestingAdminEditUser_thenDoNotUpdateEndUserAndReturnToAdminAccountFormView() throws Exception {
        RequestBuilder request = post("/admin-edit-user")
                .param("endUserID", "1")
                .param("forename", "forename")
                .param("surname", "")
                .param("username", "username")
                .param("email", "email@email.com")
                .param("password", "")
                .param("role", Role.ADMIN.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(prefixView + "admin-account-form.jsp"))
                .andExpect(view().name("admin-account-form"))
                .andExpect(model().attribute("endUserID", is(1L)))
                .andExpect(model().attribute("endUser", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("endUser", hasProperty("surname", is(""))))
                .andExpect(model().attribute("endUser", hasProperty("username", is("username"))))
                .andExpect(model().attribute("endUser", hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("endUser", hasProperty("password", is(""))))
                .andExpect(model().attribute("endUser", hasProperty("role", is(Role.ADMIN))));
        verifyNoInteractions(endUserService);
        verifyNoInteractions(endUserFormService);
    }

    @Test
    @WithMockUser(authorities = "A", username = "username")
    public void givenAnAdminEndUserUpdatingAnEndUserDetailsAndTheUsernameAlreadyExistButBelongToTheSameEndUser_whenRequestingAdminEditUser_thenUpdateEndUserAndRedirectToAdminPage() throws Exception {
        RequestBuilder request = post("/admin-edit-user")
                .with(csrf())
                .param("endUserID", "5")
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "oldUsername")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMIN.toString());

        EndUser oldEndUserDetails = new Admin(5L, "oldForename", "oldSurname", "oldUsername", "oldEmail@email.com", "oldPassword");
        when(endUserService.getEndUser(anyString())).thenReturn(oldEndUserDetails);
        EndUser newEndUserDetails = new Admin(5L, "forename", "surname", "oldUsername", "email@email.com", "password");
        when(endUserFormService.convertToEndUser(any())).thenReturn(newEndUserDetails);

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(endUserService).getEndUser("oldUsername");
        verify(endUserFormService).convertToEndUser(any());
        verify(endUserService).editEndUser(newEndUserDetails);
    }

    @Test
    @WithMockUser(authorities = "A", username = "username")
    public void givenAnAdminEndUserUpdatingAnEndUserDetailsAndTheUsernameAlreadyExistButDoesNotBelongToTheSameEndUser_whenRequestingAdminEditUser_thenDoNotUpdateEndUserAndReturnToAdminAccountFormView() throws Exception {
        RequestBuilder request = post("/admin-edit-user")
                .with(csrf())
                .param("endUserID", "1")
                .param("forename", "forename")
                .param("surname", "surname")
                .param("username", "username2")
                .param("email", "email@email.com")
                .param("password", "password")
                .param("role", Role.ADMIN.toString());

        EndUser anotherEndUser = new Instructor(2L, "forename2", "surname2", "username2", "email@eamil.com2", "password2");
        when(endUserService.getEndUser(anyString())).thenReturn(anotherEndUser);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-account-form"))
                .andExpect(model().attribute("endUserID", is(1L)))
                .andExpect(model().attribute("endUser", hasProperty("forename", is("forename"))))
                .andExpect(model().attribute("endUser", hasProperty("surname", is("surname"))))
                .andExpect(model().attribute("endUser", hasProperty("username", is("username2"))))
                .andExpect(model().attribute("endUser", hasProperty("email", is("email@email.com"))))
                .andExpect(model().attribute("endUser", hasProperty("password", is("password"))))
                .andExpect(model().attribute("endUser", hasProperty("role", is(Role.ADMIN))));
        verify(endUserService).getEndUser("username2");
        verifyNoMoreInteractions(endUserService);
        verifyNoInteractions(endUserFormService);
    }
}
