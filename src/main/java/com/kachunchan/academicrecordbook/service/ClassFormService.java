package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Class;
import com.kachunchan.academicrecordbook.domain.ClassForm;

public interface ClassFormService {

    Class convertToClass(ClassForm classForm);
    ClassForm convertToClassForm(Class classs);
}
