package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.domain.SubjectForm;
import org.springframework.stereotype.Service;

@Service
public class SubjectFormServiceImpl implements SubjectFormService {

    public SubjectFormServiceImpl(){}

    @Override
    public Subject convertToSubject(SubjectForm subjectForm) {
        return new Subject(subjectForm.getCode(), subjectForm.getTitle());
    }

    @Override
    public SubjectForm convertToSubjectForm(Subject subject) {
        SubjectForm subjectForm = new SubjectForm();
        subjectForm.setCode(subject.getCode());
        subjectForm.setTitle(subject.getTitle());
        return subjectForm;
    }
}
