package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void givenAValidExistingUsername_whenGettingAnAccount_thenReturnAccount() {
        Account stubbedAccount = new Account(1L, "forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(stubbedAccount);

        Account account = service.getAnAccount("username");
        assertEquals(stubbedAccount, account);
    }

    @Test
    public void givenANonExistingUsername_whenGettingANonExistingAccount_thenReturnNull() {
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(null);
        assertNull(service.getAnAccount("username"));
    }

    @Test
    public void givenANonExistingUsername_whenAddingANewAccount_thenReturnAccount() {
        Account newAccount = new Account("forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        Account stubbedAccount = new Account(1L, "forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        when(accountRepository.save(any())).thenReturn(stubbedAccount);
        assertEquals(stubbedAccount, service.addAccount(newAccount));
    }
}
