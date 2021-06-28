package com.kachunchan.academicrecordbook.repository;

import com.kachunchan.academicrecordbook.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getAnAccountByUsername(String username);
}
