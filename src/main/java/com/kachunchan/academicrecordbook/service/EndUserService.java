package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.EndUser;

import java.util.List;

public interface EndUserService {

    EndUser addEndUser(EndUser endUser);
    EndUser getEndUser(long id);
    EndUser getEndUser(String username);
    List<EndUser> getAllEndUsers();
    EndUser editEndUser(EndUser endUser);
    void deleteEndUser(long id);
}
