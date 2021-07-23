package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EndUserFormServiceImpl implements EndUserFormService {

    private final PasswordEncoderService passwordEncoderService;

    public EndUserFormServiceImpl(PasswordEncoderService passwordEncoderService) {
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public EndUser convertToEndUser(EndUserForm endUserForm) {
        String encodedPassword = passwordEncoderService.encodePassword(endUserForm.getPassword());
        Role selectedRole = endUserForm.getRole();
        if (selectedRole == Role.ADMIN) {
            return new Admin(endUserForm.getForename(), endUserForm.getSurname(), endUserForm.getUsername(), endUserForm.getEmail(), encodedPassword);
        } else if (selectedRole == Role.INSTRUCTOR) {
            return new Instructor(endUserForm.getForename(), endUserForm.getSurname(), endUserForm.getUsername(), endUserForm.getEmail(), encodedPassword);
        } else {
            return new Student(endUserForm.getForename(), endUserForm.getSurname(), endUserForm.getUsername(), endUserForm.getEmail(), encodedPassword);
        }
    }

    @Override
    public EndUserForm convertToEndUserForm(EndUser endUser) {
        EndUserForm endUserForm = new EndUserForm();
        endUserForm.setForename(endUser.getForename());
        endUserForm.setSurname(endUser.getSurname());
        endUserForm.setUsername(endUser.getUsername());
        endUserForm.setEmail(endUser.getEmail());
        endUserForm.setPassword(passwordEncoderService.decodePassword(endUser.getPassword()));
        endUserForm.setRole(endUser.getRole());
        return endUserForm;
    }
}
