package com.kachunchan.academicrecordbook.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository repository;

    @Test
    public void whenRetrievingAValidAndExistingAccount_ThenReturnAccount() {
        Account stubbedAccount = new Account("forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        Account expectedAccount = new Account(1L,"forename", "surname", "username", "email", "password", Role.ADMINISTRATOR);
        // The account should automatically assign an ID so ID is omitted in stubbedAccount.
        entityManager.persistFlushFind(stubbedAccount);
        Account account = repository.getAnAccountByUsername("username");
        assertThat(account.equals(expectedAccount));
    }

    @Test
    public void whenRetrievingANonExistingAccount_ThenReturnNull() {
        Account account = repository.getAnAccountByUsername("username");
        assertThat(account == null);
    }
}
