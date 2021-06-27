package com.kachunchan.academicrecordbook.security.service;

import com.kachunchan.academicrecordbook.account.domain.Account;
import com.kachunchan.academicrecordbook.account.domain.Role;
import com.kachunchan.academicrecordbook.account.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityLoginServiceTest {

    private SecurityLoginService securityLoginService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        this.securityLoginService = new SecurityLoginService(accountRepository);
    }

    @Test
    public void whenLoadUserByUsernameWithValidCredientails_ThenReturnUserDetails(){
        UserDetails expected = User.withUsername("dummy").password("password").authorities("ADMINISTRATOR").build();
        Account stubbedAccount = new Account ("dummyForename", "dummyForename", "dummy", "dummy@email", "password", Role.ADMINISTRATOR);
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(stubbedAccount);
        UserDetails actualResult = securityLoginService.loadUserByUsername("dummy");
        Assertions.assertEquals(expected, actualResult);
    }
}
