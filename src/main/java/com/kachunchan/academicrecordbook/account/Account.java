package com.kachunchan.academicrecordbook.account;

import javax.persistence.*;

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

    protected Account( String forename, String surname, String username, String email, String password, Role role) {
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    protected Account(long id, String forename, String surname, String username, String email, String password, Role role) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
