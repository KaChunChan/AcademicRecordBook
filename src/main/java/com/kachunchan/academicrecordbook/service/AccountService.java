package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;

public interface AccountService {

    Account getAnAccount(String username);
    Account addAccount(Account newAccount);
}
