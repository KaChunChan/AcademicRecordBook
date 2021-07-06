package com.kachunchan.academicrecordbook.service;

import org.springframework.stereotype.Service;

@Service
public class NoopPasswordEncoderService implements  PasswordEncoderService {
    @Override
    public String encodePassword(String password) {
        StringBuilder encodedPassword = new StringBuilder("{noop}");
        encodedPassword.append(password);
        return encodedPassword.toString();
    }
}
