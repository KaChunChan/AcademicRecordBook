package com.kachunchan.academicrecordbook.account.repository;

import com.kachunchan.academicrecordbook.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getAnAccountByUsername(String username);
}
