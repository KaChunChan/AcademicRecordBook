package com.kachunchan.academicrecordbook.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAnAccount(String username) {
        Account account = accountRepository.getAnAccountByUsername(username);
        if (account == null) {
            throw new AccountDoesNotExistException();
        }
        return account;
    }
}
