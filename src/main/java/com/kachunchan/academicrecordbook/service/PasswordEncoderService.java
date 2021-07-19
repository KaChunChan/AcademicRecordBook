package com.kachunchan.academicrecordbook.service;

public interface PasswordEncoderService {

    String encodePassword(String password);
    String decodePassword(String password);
}
