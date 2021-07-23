package com.kachunchan.academicrecordbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("S")
@Table(name = "STUDENTS")
public class Student extends EndUser {

    @ElementCollection
    private Map<Class, Grade> transcript;

    public Student(long id, String forename, String surname, String username, String email, String password) {
        super(id, forename, surname, username, email, password, Role.STUDENT);
    }

    public Student(String forename, String surname, String username, String email, String password) {
        super(forename, surname, username, email, password, Role.STUDENT);
    }
}
