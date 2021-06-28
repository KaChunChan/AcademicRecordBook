package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.Role;
import com.kachunchan.academicrecordbook.exception.AccountDoesNotExistException;
import com.kachunchan.academicrecordbook.repository.AccountRepository;
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
    public void givenAValidExistingUsername_whenGettingAnAccount_thenReturnAccount() {
        Account stubbedAccount = new Account(1L, "forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(stubbedAccount);

        Account account = service.getAnAccount("username");
        assertEquals(stubbedAccount, account);
    }

    @Test
    public void givenANonExistingUsername_whenGettingANonExistingAccount_thenThrowAccountDoesNotExistException() {
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(null);
        assertThrows(AccountDoesNotExistException.class, () -> service.getAnAccount("username"));
    }
}
