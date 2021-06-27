package com.kachunchan.academicrecordbook.account.repository;

import com.kachunchan.academicrecordbook.account.domain.Account;
import com.kachunchan.academicrecordbook.account.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository repository;

    @Test
    public void whenRetrievingAValidAndExistingAccount_ThenReturnAccount() {
        Account stubbedAccount = new Account("forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        // The account should automatically assign an ID so ID is omitted in stubbedAccount.
        Long persistedID = entityManager.persistAndGetId(stubbedAccount, Long.class);
        Account expectedAccount = new Account(persistedID.longValue(),"forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        Account account = repository.getAnAccountByUsername("username");
        assertEquals(expectedAccount, account);
    }

    @Test
    public void whenRetrievingANonExistingAccount_ThenReturnNull() {
        Account account = repository.getAnAccountByUsername("username");
        assertNull(account);
    }
}
