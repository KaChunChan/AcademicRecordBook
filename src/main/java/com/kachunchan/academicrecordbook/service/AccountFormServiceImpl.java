package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.AccountForm;
import org.springframework.stereotype.Service;

@Service
public class AccountFormServiceImpl implements AccountFormService {

    private  final PasswordEncoderService passwordEncoderService;

    public AccountFormServiceImpl(PasswordEncoderService passwordEncoderService) {
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public Account makeIntoAccount(AccountForm form) {
        Account account = new Account();
        account.setForename(form.getForename());
        account.setSurname(form.getSurname());
        account.setUsername(form.getUsername());
        account.setEmail(form.getEmail());
        account.setPassword(passwordEncoderService.encodePassword(form.getPassword()));
        account.setRole(form.getRole());
        return account;
    }
}
