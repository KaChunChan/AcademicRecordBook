package com.kachunchan.academicrecordbook.repository;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@TestPropertySource(locations = {"classpath:test.properties"})
@Sql(scripts={"classpath:schema-test.sql","classpath:data-test.sql"})
public class AccountRepositoryTest {

    @Autowired
    public AccountRepository accountRepository;

    @Test
    public void givenAValidExistingUsername_whenRetrievingTheAccountByTheUsername_thenReturnAccount() {
        Account retrievedAccount = accountRepository.getAnAccountByUsername("jillbill");
        Account expectedAccount = new Account(3, "Jill", "Bill", "jillbill","jill.bill@email.com","jill", Role.STUDENT);
        assertEquals(expectedAccount, retrievedAccount);
    }

    @Test
    public void givenANonExistingUsername_whenRetrievingTheAccountByTheUsername_ThenReturnNull() {
        Account account = accountRepository.getAnAccountByUsername("username");
        assertNull(account);
    }

    @Test
    public void givenANonExistingUsername_whenAddingANewAccount_thenReturnAccountWithID() {
        int expectedID = accountRepository.findAll().size() + 1 ;
        System.out.println("The expected ID is " + expectedID);
        Account newUser = new Account("newForename", "newSurname", "newUsername", "newEmail@email.com", "newPassword", Role.STUDENT);
        Account expectedAccount = new Account(expectedID, "newForename", "newSurname", "newUsername", "newEmail@email.com", "newPassword", Role.STUDENT);
        assertEquals(expectedAccount, accountRepository.save(newUser));
    }
}
