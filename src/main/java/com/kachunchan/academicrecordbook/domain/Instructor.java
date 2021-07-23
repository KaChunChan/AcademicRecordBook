package com.kachunchan.academicrecordbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("I")
@Table(name = "INSTRUCTORS")
public class Instructor extends EndUser {

    @OneToMany
    @JoinColumn(name = "CLASS_ID")
    private Set<Class> classes;

    public Instructor(long id, String forename, String surname, String username, String email, String password) {
        super(id, forename, surname, username, email, password, Role.INSTRUCTOR);
    }

    public Instructor(String forename, String surname, String username, String email, String password) {
        super(forename, surname, username, email, password, Role.INSTRUCTOR);
    }
}
