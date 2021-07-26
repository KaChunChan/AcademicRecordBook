package com.kachunchan.academicrecordbook.repository;

import com.kachunchan.academicrecordbook.domain.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, String> {

}
