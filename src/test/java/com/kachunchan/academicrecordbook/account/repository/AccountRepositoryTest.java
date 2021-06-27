package com.kachunchan.academicrecordbook.account.repository;

import com.kachunchan.academicrecordbook.account.domain.Account;
import com.kachunchan.academicrecordbook.account.domain.Role;
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
    public void whenRetrievingAValidAndExistingAccount_ThenReturnAccount() {
        Account retrievedAccount = accountRepository.getAnAccountByUsername("jillbill");
        Account expectedAccount = new Account(3, "Jill", "Bill", "jillbill","jill.bill@email.com","jill", Role.STUDENT);
        assertEquals(expectedAccount, retrievedAccount);
    }

    @Test
    public void whenRetrievingANonExistingAccount_ThenReturnNull() {
        Account account = accountRepository.getAnAccountByUsername("username");
        assertNull(account);
    }
}
