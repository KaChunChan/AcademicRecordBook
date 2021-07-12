package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAnAccount(String username) {
        return accountRepository.getAnAccountByUsername(username);
    }

    public Account addAccount(Account account) {
        return accountRepository.save((account));
    }

}
