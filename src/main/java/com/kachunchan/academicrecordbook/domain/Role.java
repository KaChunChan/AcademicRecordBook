package com.kachunchan.academicrecordbook.domain;

public enum Role {

    ADMIN("A"),
    INSTRUCTOR("I"),
    STUDENT("S");

    private String code;

    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
