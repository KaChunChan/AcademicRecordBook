package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;

import java.util.List;

public interface AccountService {

    Account getAnAccount(String username);
    Account getAnAccount(long id);
    Account addAccount(Account newAccount);
    List<Account> getAllAccounts();
    void deleteAccount(Long id);
}
