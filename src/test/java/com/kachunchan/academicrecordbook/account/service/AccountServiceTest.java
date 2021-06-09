package com.kachunchan.academicrecordbook.account.service;

import com.kachunchan.academicrecordbook.account.domain.Account;
import com.kachunchan.academicrecordbook.account.domain.Role;
import com.kachunchan.academicrecordbook.account.exception.AccountDoesNotExistException;
import com.kachunchan.academicrecordbook.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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
    public void whenGettingAnValidExistingAccount_ThenReturnAccount() {
        Account stubbedAccount = new Account(1L, "forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(stubbedAccount);

        Account account = service.getAnAccount("username");
        assertEquals(stubbedAccount, account);
    }

    @Test
    public void whenGettingANonExistingAccount_ThenThrowAccountDoesNotExistException() {
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(null);
        assertThrows(AccountDoesNotExistException.class, () -> service.getAnAccount("username"));
    }
}
