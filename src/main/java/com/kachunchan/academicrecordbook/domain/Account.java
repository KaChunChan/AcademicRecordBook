package com.kachunchan.academicrecordbook.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ACCOUNT")
public class Account {

    @Id
    @Column
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

    public Account() {

    }

    public Account( String forename, String surname, String username, String email, String password, Role role) {
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Account(long id, String forename, String surname, String username, String email, String password, Role role) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
