package com.kachunchan.academicrecordbook.account;

public enum Role {

    ADMINISTRATOR("A"),
    INSTRUCTOR("I"),
    STUDENT("S");

    private String code;

    private Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
