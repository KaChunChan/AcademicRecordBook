package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.EndUser;
import com.kachunchan.academicrecordbook.domain.EndUserForm;

public interface EndUserFormService {

    EndUser convertToEndUser(EndUserForm endUserForm);
    EndUserForm convertToEndUserForm(EndUser endUser);
}
