package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.domain.SubjectForm;

public interface SubjectFormService {

    Subject convertToSubject(SubjectForm subjectForm);
    SubjectForm convertToSubjectForm(Subject subject);
}
