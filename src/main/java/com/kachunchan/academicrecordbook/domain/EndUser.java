package com.kachunchan.academicrecordbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@DiscriminatorColumn(name="ENDUSER_TYPE")
@Table(name = "ENDUSERS")
public abstract class EndUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String forename;
    @Column
    private String surname;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Role role;

    public EndUser(long id, String forename, String surname, String username, String email, String password, Role role) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public EndUser(String forename, String surname, String username, String email, String password, Role role) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
