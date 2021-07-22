package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.Subject;
import com.kachunchan.academicrecordbook.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject addSubject(Subject subject) {
        if (subjectRepository.existsById(subject.getCode())) {
            return null;
        }
        return subjectRepository.save(subject);
    }

    @Override
    public Subject getSubject(String id) {
        if (subjectRepository.existsById(id)) {
            return subjectRepository.getOne(id);
        }
        return null;
    }

    @Override
    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject editSubject(Subject subject) {
        if (subjectRepository.existsById(subject.getCode())) {
            return subjectRepository.save(subject);
        }
        return null;
    }

    @Override
    public void deleteSubject(String id) {
        subjectRepository.deleteById(id);
    }
}
