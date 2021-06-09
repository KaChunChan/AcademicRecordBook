package com.kachunchan.academicrecordbook.account.service;

import com.kachunchan.academicrecordbook.account.domain.Account;
import com.kachunchan.academicrecordbook.account.exception.AccountDoesNotExistException;
import com.kachunchan.academicrecordbook.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
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
