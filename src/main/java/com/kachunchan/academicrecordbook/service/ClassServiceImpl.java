package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Class;
import com.kachunchan.academicrecordbook.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public Class addClass(Class classs) {
        if(classRepository.existsById(classs.getCode())) {
            return null;
        }
        return classRepository.save(classs);
    }

    @Override
    public Class getClass(String id) {
        if (classRepository.existsById(id)){
            return classRepository.getOne(id);
        }
        return null;
    }

    @Override
    public List<Class> getAllClass() {
        return classRepository.findAll();
    }

    @Override
    public Class editClass(Class classs) {
        if (classRepository.existsById(classs.getCode())) {
            return classRepository.save(classs);
        }
        return null;
    }

    @Override
    public void deleteClass(String id) {
        classRepository.deleteById(id);
    }
}
