package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EndUserFormServiceImplTest {

    private EndUserFormService endUserFormService;
    @Mock
    PasswordEncoderService passwordEncoderService;

    @BeforeEach
    public void setup() {
        endUserFormService = new EndUserFormServiceImpl(passwordEncoderService);
    }

    //Test convertToEndUser(EndUserFrom)

    @Test
    public void givenEndUserFormWithRoleAsAdmin_whenConvertToEndUser_thenReturnEndUserWithRoleAsAdminAndPasswordIsEncoded() {
        EndUserForm endUserForm = new EndUserForm();
        endUserForm.setForename("forename");
        endUserForm.setSurname("surname");
        endUserForm.setUsername("username");
        endUserForm.setEmail("email@email.com");
        endUserForm.setPassword("password");
        endUserForm.setRole(Role.ADMIN);
        when(passwordEncoderService.encodePassword(anyString())).thenReturn("{noop}password");

        EndUser expectedEndUser = new Admin("forename", "surname", "username", "email@email.com", "{noop}password");
        EndUser actualEndUser = endUserFormService.convertToEndUser(endUserForm);

        assertEquals(expectedEndUser, actualEndUser);
        verify(passwordEncoderService).encodePassword("password");
    }

    @Test
    public void givenEndUserFormWithRoleAsInstructor_whenConvertToEndUser_thenReturnEndUserWithRoleAsInstructorAndPasswordIsEncoded() {
        EndUserForm endUserForm = new EndUserForm();
        endUserForm.setForename("forename");
        endUserForm.setSurname("surname");
        endUserForm.setUsername("username");
        endUserForm.setEmail("email@email.com");
        endUserForm.setPassword("password");
        endUserForm.setRole(Role.INSTRUCTOR);
        when(passwordEncoderService.encodePassword(anyString())).thenReturn("{noop}password");

        EndUser expectedEndUser = new Instructor("forename", "surname", "username", "email@email.com", "{noop}password");
        EndUser actualEndUser = endUserFormService.convertToEndUser(endUserForm);

        assertEquals(expectedEndUser, actualEndUser);
        verify(passwordEncoderService).encodePassword("password");
    }

    @Test
    public void givenEndUserFormWithRoleAsStudent_whenConvertToEndUser_thenReturnEndUserWithRoleAsStudentAndPasswordIsEncoded() {
        EndUserForm endUserForm = new EndUserForm();
        endUserForm.setForename("forename");
        endUserForm.setSurname("surname");
        endUserForm.setUsername("username");
        endUserForm.setEmail("email@email.com");
        endUserForm.setPassword("password");
        endUserForm.setRole(Role.STUDENT);
        when(passwordEncoderService.encodePassword(anyString())).thenReturn("{noop}password");

        EndUser expectedEndUser = new Student("forename", "surname", "username", "email@email.com", "{noop}password");
        EndUser actualEndUser = endUserFormService.convertToEndUser(endUserForm);

        assertEquals(expectedEndUser, actualEndUser);
        verify(passwordEncoderService).encodePassword("password");
    }

    //Test convertToEndUserForm

    @Test
    public void givenEndUserWithRoleAsAdmin_whenConvertToEndUserForm_thenReturnEndUserFormWithRoleAsAdminAndPasswordDecoded() {
        EndUser endUser = new Admin(1l, "forename", "surname", "username", "email@email.com", "{noop}password");
        when(passwordEncoderService.decodePassword(anyString())).thenReturn("password");

        EndUserForm expectedEndUserForm = new EndUserForm();
        expectedEndUserForm.setForename("forename");
        expectedEndUserForm.setSurname("surname");
        expectedEndUserForm.setUsername("username");
        expectedEndUserForm.setEmail("email@email.com");
        expectedEndUserForm.setPassword("password");
        expectedEndUserForm.setRole(Role.ADMIN);
        EndUserForm actualEndUserForm = endUserFormService.convertToEndUserForm(endUser);

        assertEquals(expectedEndUserForm, actualEndUserForm);
        verify(passwordEncoderService).decodePassword("{noop}password");
    }

    @Test
    public void givenEndUserWithRoleAsInstructor_whenConvertToEndUserForm_thenReturnEndUserFormWithRoleAsInstructorAndPasswordDecoded() {
        EndUser endUser = new Instructor(1l, "forename", "surname", "username", "email@email.com", "{noop}password");
        when(passwordEncoderService.decodePassword(anyString())).thenReturn("password");

        EndUserForm expectedEndUserForm = new EndUserForm();
        expectedEndUserForm.setForename("forename");
        expectedEndUserForm.setSurname("surname");
        expectedEndUserForm.setUsername("username");
        expectedEndUserForm.setEmail("email@email.com");
        expectedEndUserForm.setPassword("password");
        expectedEndUserForm.setRole(Role.INSTRUCTOR);
        EndUserForm actualEndUserForm = endUserFormService.convertToEndUserForm(endUser);

        assertEquals(expectedEndUserForm, actualEndUserForm);
        verify(passwordEncoderService).decodePassword("{noop}password");
    }

    @Test
    public void givenEndUserWithRoleAsStudent_whenConvertToEndUserForm_thenReturnEndUserFormWithRoleAsStudentAndPasswordDecoded() {
        EndUser endUser = new Student(1l, "forename", "surname", "username", "email@email.com", "{noop}password");
        when(passwordEncoderService.decodePassword(anyString())).thenReturn("password");

        EndUserForm expectedEndUserForm = new EndUserForm();
        expectedEndUserForm.setForename("forename");
        expectedEndUserForm.setSurname("surname");
        expectedEndUserForm.setUsername("username");
        expectedEndUserForm.setEmail("email@email.com");
        expectedEndUserForm.setPassword("password");
        expectedEndUserForm.setRole(Role.STUDENT);
        EndUserForm actualEndUserForm = endUserFormService.convertToEndUserForm(endUser);

        assertEquals(expectedEndUserForm, actualEndUserForm);
        verify(passwordEncoderService).decodePassword("{noop}password");
    }
}
