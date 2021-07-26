package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Class;
import com.kachunchan.academicrecordbook.domain.ClassForm;
import org.springframework.stereotype.Service;

@Service
public class ClassFormServiceImpl implements ClassFormService {
    @Override
    public Class convertToClass(ClassForm classForm) {
        return new Class(classForm.getCode(), classForm.getSubject());
    }

    @Override
    public ClassForm convertToClassForm(Class classs) {
        ClassForm classForm = new ClassForm();
        classForm.setCode(classs.getCode());
        classForm.setSubject(classs.getSubject());
        return classForm;
    }
}
