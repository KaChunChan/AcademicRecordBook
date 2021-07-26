package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Class;

import java.util.List;

public interface ClassService {

    Class addClass(Class classs);
    Class getClass(String id);
    List<Class> getAllClass();
    Class editClass(Class classs);
    void deleteClass(String id);
}
