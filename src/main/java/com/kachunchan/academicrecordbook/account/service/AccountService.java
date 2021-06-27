package com.kachunchan.academicrecordbook.account.service;

import com.kachunchan.academicrecordbook.account.domain.Account;

public interface AccountService {

    Account getAnAccount(String username);
}
