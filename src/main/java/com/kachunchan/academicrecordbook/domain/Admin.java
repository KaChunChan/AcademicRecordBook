package com.kachunchan.academicrecordbook.domain;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@DiscriminatorValue("A")
@Table(name = "ADMINS")
public class Admin extends EndUser {

    public Admin(long id, String forename, String surname, String username, String email, String password) {
        super(id, forename, surname, username, email, password, Role.ADMIN);
    }

    public Admin(String forename, String surname, String username, String email, String password) {
        super(forename, surname, username, email, password, Role.ADMIN);
    }
}
