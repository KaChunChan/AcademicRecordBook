package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Account;
import com.kachunchan.academicrecordbook.domain.AccountForm;

public interface AccountFormService {

    Account makeIntoAccount(AccountForm form);
}
