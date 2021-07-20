package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Admin;
import com.kachunchan.academicrecordbook.domain.EndUser;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.repository.EndUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    private LoginService loginService;
    @Mock
    private EndUserRepository endUserRepository;

    @BeforeEach
    public void setup() {
        this.loginService = new LoginService(endUserRepository);
    }

    @Test
    public void givenAnExistingUsernameThatBelongsToAnEndUser_whenLoadUserByUsernameHasBeenInvoked_thenReturnUserDetails() {
        EndUser endUser = new Admin("forename", "surname", "username", "email@email.com", "password");
        when(endUserRepository.findByUsername(anyString())).thenReturn(endUser);

        UserDetails expectedUserDetails = User.withUsername("username").password("password").authorities(Role.ADMIN.getCode()).build();
        System.out.println("### The Expected is : " + expectedUserDetails);
        UserDetails actualUserDetails = loginService.loadUserByUsername("username");
        System.out.println("### The actual is : " + actualUserDetails);
        assertEquals(expectedUserDetails, actualUserDetails);
        verify(endUserRepository).findByUsername("username");
    }
}
