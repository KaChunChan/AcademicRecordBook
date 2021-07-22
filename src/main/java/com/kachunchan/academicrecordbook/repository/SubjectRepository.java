package com.kachunchan.academicrecordbook.repository;

import com.kachunchan.academicrecordbook.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
}