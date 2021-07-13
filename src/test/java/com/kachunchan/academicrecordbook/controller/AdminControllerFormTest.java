package com.kachunchan.academicrecordbook.controller;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class AdminControllerFormTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountFormService accountFormService;

    /* FORENAME TESTS - Focus on forename field tests whilst all other fields are valid - VALID FORENAME*/

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameIsMoreThanOneCharacterAndLessThanThirtyOneCharacters_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String forename = "abc";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameIsOneCharacter_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String forename = "a";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameIsThirtyCharacters_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String forename = "abcdefghijklmnopqrstuvwxyz1234";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasTwoNamesSeparatedByOneBlankSpace_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String forename = "hello world";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasTwoNamesSeparatedThreeBlankSpace_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String forename = "hello   world";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasThreeNamesSeparatedByOneBlankSpace_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String forename = "hello world again";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasAHyphen_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String forename = "hello-world";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    /* FORENAME TESTS - Focus on forename field tests whilst all other fields are valid - INVALID FORENAME*/

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameIsLessThanOneCharacter_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameIsMoreThanThirtyCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "abcdefghijklmnopqrstuvwxyz12345";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameContainsOnlyBlankSpaces_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "  ";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "  ")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasALongStringOfCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "1234567890qertyuiopasdfghjklzxcvbnm,.[]asdhlk]';,.,m.lhj'@kjlkjdlad13489509soifjsknwekfbkjsfsdhfwekfhksdhfwekfhs irhwek  hsih sksisehf wehuisfwefhui    euifhehfshfskdhf eoh ";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasBlankSpacesBeforeTheForename_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "  forename";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasBlankSpacesAfterTheForename_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "forename  ";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasJustAnAtSymbol_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "@";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasDoubleAtSymbols_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "@@";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameHasHyphenAtTheStart_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = "-forename";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenForenameIsNull_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String forename = null;
        RequestBuilder request = post("/admin-add-user")
                .param("forename", forename)
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    /* SURNAME TESTS - Omitted as regex is same for forename - test is covered in FORENAME TESTS */

    /* USERNAME TESTS - Focus on username field tests whilst all other fields are valid - VALID USERNAME */

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameIsMoreThanThTwoCharactersAndLessThanThirtyOneCharacters_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "username";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasPeriods_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "user.name.field";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasUnderscores_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "user_name_field";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasUnderscoreAtTheStart_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "_username";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasUnderscoreAtTheEnd_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "username_";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasHyphens_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "user-name-field";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasHyphensAtTheStart_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "-username";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasHyphensAtTheEnd_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String username = "username-";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    /* USERNAME TESTS - Focus on username field tests whilst all other fields are valid - INVALID USERNAME*/

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameIsLessThanThreeCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = "Hi";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameIsExceedsThirtyCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = "abcdefghijklmnopqrstuvwxyz1234567890";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasSpaces_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = "user name";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasSpacesAtTheStart_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = " user.name";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasSpacesAtTheEnd_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = "user.name ";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasPeriodAtTheStart_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = ".user.name";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasPeriodAtTheEnd_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = "user.name.";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasOtherCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = "user=name";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameIsNull_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String username = null;
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", username)
                .param("email", "abc@email.com")
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    /* E-MAIL TESTS - Focus on e-mail field tests whilst all other fields are valid - VALID E-MAIL */

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameIsMoreThanTwoCharactersAndLessThanSixtyOneCharacters_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String email = "email@email.com";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenUsernameHasUnderscoresAndHyphen_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String email = "email-email_email.email@email.com";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    /* E-MAIL TESTS - Focus on e-mail field tests whilst all other fields are valid - INVALID E-MAIL*/

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailIsLessThanThreeCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "h@";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailIsExceedsSixtyCharactres_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "abcdefghijklmnopqrstuvwxyz1234@abcdefghijklmnopqrstuvwxyz1234567890";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailDoesNotHaveAnAtSymbol_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "email.com";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailHasMoreThanOneAtSymbol_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "email@email@email.com";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailHasAtSymbolAtTheStart_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "@email.com";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailHasAtSymbolAtTheEnd_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "email.com@";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailHasUnderscoreAtTheStart_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "_email@email.com";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailHasUnderscoreAtTheEnd_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "email@email.com_";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailHasHyphenAtTheStart_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "-email@email.com";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailHasHyphenAtTheEnd_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = "email@email.com-";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenEmailIsNull_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String email = null;
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "username")
                .param("email", email)
                .param("password", "abcdef")
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    /* PASSWORD TESTS - Focus on password field tests whilst all other fields are valid - VALID password*/

    @Test
    @WithMockUser(roles = "A")
    public void givenPasswordIsMoreThanFourCharactersAndLessThanThirtyOneCharacters_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String password = "MoreThanFour";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", password)
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    /* PASSWORD TESTS - Focus on password field tests whilst all other fields are valid - INVALID password*/

    @Test
    @WithMockUser(roles = "A")
    public void givenPasswordHasLessThanFiveCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String password = "Two";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", password)
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenPasswordHasMoreThanThirtyCharacters_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String password = "abcdefghijklmnopqrstuvwxyz1234567890";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", password)
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenPasswordIsNull_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String password = null;
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", password)
                .param("role", Role.ADMINISTRATOR.toString())
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    /* ROLE TESTS - Focus on role field tests whilst all other fields are valid - VALID ROLE*/

    @Test
    @WithMockUser(roles = "A")
    public void givenRoleHasAValidRole_whenFormIsSubmitted_thenProceedWithUserCreation() throws Exception {
        String role = Role.INSTRUCTOR.toString();
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "password")
                .param("role", role)
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin"));
        verify(accountService).addAccount(any());
    }

    /* ROLE TESTS - Focus on role field tests whilst all other fields are valid - INVALID ROLE*/

    @Test
    @WithMockUser(roles = "A")
    public void givenRoleIsNotValid_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String role = "hacker-man";
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "password")
                .param("role", role)
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }

    @Test
    @WithMockUser(roles = "A")
    public void givenRoleIsNull_whenFormIsSubmitted_thenDoNotAddUserAndReturnBackToAdminAddUserPage() throws Exception {
        String role = null;
        RequestBuilder request = post("/admin-add-user")
                .param("forename", "abc")
                .param("surname", "abc")
                .param("username", "abc")
                .param("email", "abc@email.com")
                .param("password", "password")
                .param("role", role)
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add-user"))
                .andExpect(forwardedUrl("/WEB-INF/view/admin-add-user.jsp"));
        verifyNoInteractions(accountService);
    }
}
