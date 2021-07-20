package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Admin;
import com.kachunchan.academicrecordbook.domain.EndUser;
import com.kachunchan.academicrecordbook.repository.EndUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EndUserServiceImplTest {

    private EndUserService endUserService;
    @Mock
    private EndUserRepository endUserRepository;

    @BeforeEach
    public void setup() {
        endUserService = new EndUserServiceImpl(endUserRepository);
    }

    //Test addEndUser(EndUser endUser)

    @Test
    public void givenNonExistingEndUserWithUniqueUsername_whenAddEndUserHasBeenInvoked_thenPersistToRepositoryAndReturnEndUser() {
        EndUser endUser = new Admin("forename", "surname", "username", "email@email.com", "password");
        EndUser returnedEndUser = new Admin(1L,"forename", "surname", "username", "email@email.com", "password");
        when(endUserRepository.save(any(EndUser.class))).thenReturn(returnedEndUser);

        EndUser expectedEndUser = returnedEndUser;
        EndUser actualEndUser = endUserService.addEndUser(endUser);

        assertEquals(expectedEndUser, actualEndUser);
        verify(endUserRepository).save(endUser);
    }

    @Test
    public void givenAnExistingEndUserWithExistingUsername_whenAddEndUserHasBeenInvoked_thenDoNotPersistEndUserToRepositoryAndReturnNull() {
        EndUser endUser = new Admin("forename", "surname", "username", "email@email.com", "password");
        EndUser dummyEndUser = new Admin();
        when(endUserRepository.findByUsername(anyString())).thenReturn(dummyEndUser);

        EndUser actualEndUser = endUserService.addEndUser(endUser);

        assertNull(actualEndUser);
        verify(endUserRepository).findByUsername("username");
        verifyNoMoreInteractions(endUserRepository);
    }

    //Test getEndUser(long id)

    @Test
    public void givenAnIDThatBelongsToAnExistingEndUser_whenGetEndUserHasBeenInvoked_thenReturnEndUser() {
        EndUser returnedEndUser = new Admin(1L,"forename", "surname", "username", "email@email.com", "password");
        when(endUserRepository.existsById(anyLong())).thenReturn(true);
        when(endUserRepository.getOne(anyLong())).thenReturn(returnedEndUser);

        EndUser expectedEndUser = returnedEndUser;
        EndUser actualEndUser = endUserService.getEndUser(1L);

        assertEquals(expectedEndUser, actualEndUser);
        verify(endUserRepository).existsById(1L);
        verify(endUserRepository).getOne(1L);
    }

    @Test
    public void givenAnIDThatDoesNotBelongToAnyExistingEndUser_whenGetEndUserHasBeenInvoked_thenReturnNull() {
        when(endUserRepository.existsById(anyLong())).thenReturn(false);

        EndUser actualEndUser = endUserService.getEndUser(1L);

        assertNull(actualEndUser);
        verify(endUserRepository).existsById(1L);
        verifyNoMoreInteractions(endUserRepository);
    }

    //Test getEndUser(String Username)

    @Test
    public void givenAnUsernameThatBelongsToAnExistingEndUser_whenGetEndUserHasBeenInvoked_thenReturnEndUser() {
        EndUser returnedEndUser = new Admin(1L,"forename", "surname", "username", "email@email.com", "password");
        when(endUserRepository.findByUsername(anyString())).thenReturn(returnedEndUser);

        EndUser expectedEndUser = returnedEndUser;
        EndUser actualEndUser = endUserService.getEndUser("username");

        assertEquals(expectedEndUser, actualEndUser);
        verify(endUserRepository).findByUsername("username");
    }

    @Test
    public void givenAnUsernameThatDoesNotBelongToAnyExistingEndUser_whenGetEndUserHasBeenInvoked_thenReturnNull() {
        when(endUserRepository.findByUsername(anyString())).thenReturn(null);

        EndUser actualEndUser = endUserService.getEndUser("username");

        assertNull(actualEndUser);
        verify(endUserRepository).findByUsername("username");
    }

    //Test getAllEndUsers()

    @Test
    public void givenTheNeedToViewAllEndUsers_whenGetAllEndUsersHasBeenInvoked_thenReturnAListOfEndUsersFromTheRepository() {
        EndUser returnedEndUser1 = new Admin(1L,"forename1", "surname1", "username1", "email1@email.com", "password");
        EndUser returnedEndUser2 = new Admin(2L,"forename2", "surname2", "username2", "email2@email.com", "password");
        EndUser returnedEndUser3 = new Admin(3L,"forename3", "surname3", "username3", "email3@email.com", "password");
        List<EndUser> endUsers = new ArrayList<>();
        endUsers.add(returnedEndUser1);
        endUsers.add(returnedEndUser2);
        endUsers.add(returnedEndUser3);
        when(endUserRepository.findAll()).thenReturn(endUsers);

        int expectedSizeOfList = 3;
        List<EndUser> actualEndUsers = endUserService.getAllEndUsers();

        assertEquals(expectedSizeOfList, actualEndUsers.size());
        verify(endUserRepository).findAll();
    }

    //Test editEndUser(EndUser endUser)

    @Test
    public void givenAnEndUserToBeUpdated_whenEditEndUserHasBeenInvoked_thenPersistIntoRepositoryAndReturnEndUser() {
        EndUser endUser = new Admin("forename", "surname", "username", "email@email.com", "password");
        EndUser returnedEndUser = new Admin(1L,"forename", "surname", "username", "email@email.com", "password");
        when(endUserRepository.save(any(EndUser.class))).thenReturn(returnedEndUser);

        EndUser expectedEndUser = returnedEndUser;
        EndUser actualEndUser = endUserService.editEndUser(endUser);

        assertEquals(expectedEndUser, actualEndUser);
        verify(endUserRepository).save(endUser);
    }

    //Test deleteEndUser(long id)

    @Test
    public void givenAnIDThatBelongsToAnExistingEndUser_whenDeleteEndUserHasBeenInvoked_thenDeleteFromRepository() {
        when(endUserRepository.existsById(anyLong())).thenReturn(true);

        endUserService.deleteEndUser(1L);

        verify(endUserRepository).existsById(1L);
        verify(endUserRepository).deleteById(1L);
    }

    @Test
    public void givenAnIDThatDoesNotBelongToAnyEndUser_whenDeleteEndUserHasBeenInvoked_thenRepositoryDoesNotDoAnythingFurther() {
        when(endUserRepository.existsById(anyLong())).thenReturn(false);

        endUserService.deleteEndUser(26L);

        verify(endUserRepository).existsById(26L);
        verifyNoMoreInteractions(endUserRepository);
    }
}
