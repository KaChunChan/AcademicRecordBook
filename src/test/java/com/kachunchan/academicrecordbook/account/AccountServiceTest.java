package com.kachunchan.academicrecordbook.account;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService service;

    @Before
    public void setup() {
        service = new AccountService(accountRepository);
    }

    @Test
    public void whenGettingAnValidExistingAccount_ThenReturnAccount() {
        Account stubbedAccount = new Account(1L, "forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(stubbedAccount);

        Account account = service.getAnAccount("username");
        assertThat(account.equals(stubbedAccount));
    }

    @Test(expected = AccountDoesNotExistException.class)
    public void whenGettingANonExistingAccount_ThenThrowAccountDoesNotExistException() {
        when(accountRepository.getAnAccountByUsername(anyString())).thenReturn(null);
        service.getAnAccount("username");
    }
}
