package com.kachunchan.academicrecordbook.repository;

import com.kachunchan.academicrecordbook.domain.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndUserRepository extends JpaRepository<EndUser, Long> {
    EndUser findByUsername(String username);
}
