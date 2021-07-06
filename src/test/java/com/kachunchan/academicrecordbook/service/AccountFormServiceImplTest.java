package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.AccountForm;
import com.kachunchan.academicrecordbook.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountFormServiceImplTest {

    private AccountFormService accountFormService;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @BeforeEach
    public void setup() {
        accountFormService = new AccountFormServiceImpl(passwordEncoderService);
    }

    @Test
    public void givenAccountFrom_whenMakingIntoAccount_thenReturnAccountWithEncryptedPasswordNoopEncoded() {
        AccountForm accountForm = new AccountForm();
        accountForm.setForename("forename");
        accountForm.setSurname("surname");
        accountForm.setUsername("username");
        accountForm.setEmail("email@email.com");
        accountForm.setPassword("password");
        accountForm.setRole(Role.ADMINISTRATOR);

        Account expectedAccount = new Account("forename", "surname", "username", "email@email.com", "{noop}password", Role.ADMINISTRATOR);
        when(passwordEncoderService.encodePassword(anyString())).thenReturn("{noop}password");
        Account actualAccount = accountFormService.makeIntoAccount(accountForm);
        assertEquals(expectedAccount, actualAccount);
    }
}
