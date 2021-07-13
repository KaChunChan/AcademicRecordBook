package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.repository.AccountRepository;
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
import static org.mockito.Mockito.verify;
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

    @Test
    public void whenGettingAllAccounts_thenReturnAListOfAccountsFromRepository() {
        Account account1 = new Account(1l, "forename1", "surname1", "username1", "email@eamil.com1", "password1", Role.ADMINISTRATOR);
        Account account2 = new Account( 2l,"forename2", "surname2", "username2", "email@eamil.com2", "password2", Role.INSTRUCTOR);
        Account account3 = new Account(3l, "forename3", "surname3", "username3", "email@eamil.com3", "password3", Role.STUDENT);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        when(accountRepository.findAll()).thenReturn(accounts);
        assertEquals(3, service.getAllAccounts().size());
    }

    @Test
    public void whenDeletingAnAccount_thenDeleteAccountFromRepository() {
        service.deleteAccount(1L);
        verify(accountRepository).deleteById(1L);
    }
}
