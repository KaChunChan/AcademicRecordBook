package com.kachunchan.academicrecordbook.repository;

import com.kachunchan.academicrecordbook.domain.Admin;
import com.kachunchan.academicrecordbook.domain.EndUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@TestPropertySource(locations = {"classpath:test.properties"})
@Sql(scripts={"classpath:data-test.sql"})
public class EndUserRepositoryTest {

    @Autowired
    private EndUserRepository endUserRepository;

    @Test
    public void givenAValidExistingUsername_whenFindByUsername_thenReturnEndUser() {
        EndUser expectedEndUser = new Admin(1L , "forename", "surname", "username", "email@email.com", "{noop}password");
        EndUser actualEndUser = endUserRepository.findByUsername("username");
        assertEquals(expectedEndUser, actualEndUser);
    }

    @Test
    public void givenANonExistingUsername_whenFindByUsername_thenReturnNull() {
        EndUser actualEndUser = endUserRepository.findByUsername("NonExistingUsername");
        assertNull(actualEndUser);
    }
}
