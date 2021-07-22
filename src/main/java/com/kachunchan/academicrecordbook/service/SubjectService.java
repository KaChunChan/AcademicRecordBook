package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Subject;

import java.util.List;

public interface SubjectService {

    Subject addSubject(Subject subject);
    Subject getSubject(String id);
    List<Subject> getAllSubject();
    Subject editSubject(Subject subject);
    void deleteSubject(String id);
}
