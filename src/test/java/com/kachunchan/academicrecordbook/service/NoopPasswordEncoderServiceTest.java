package com.kachunchan.academicrecordbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoopPasswordEncoderServiceTest {

    private PasswordEncoderService passwordEncoderService;

    @BeforeEach
    public void setup() {
        passwordEncoderService = new NoopPasswordEncoderService();
    }

    @Test
    public void givenARawPassword_whenEncodePasswordIsInvoked_thenReturnEncodedPasswordContainingNoop() {
        String rawPassword = "password";
        String expectedPassword = "{noop}password";
        String actualPassword = passwordEncoderService.encodePassword(rawPassword);
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    public void givenAnEncodedPassword_whenDecodePasswordIsInvoked_thenReturnDecodedPasswordThatDoesNotContainNoop(){
        String encodedPassword = "{noop}password{noop}password";
        String expectedPassword = "password{noop}password";
        String actualPassword = passwordEncoderService.decodePassword(encodedPassword);
        assertEquals(expectedPassword, actualPassword);
    }
}
