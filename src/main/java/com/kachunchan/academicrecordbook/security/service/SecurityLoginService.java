package com.kachunchan.academicrecordbook.security.service;

import com.kachunchan.academicrecordbook.account.domain.Account;
import com.kachunchan.academicrecordbook.account.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityLoginService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public SecurityLoginService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account retrievedAccount = accountRepository.getAnAccountByUsername(username);
        return User
                .withUsername(retrievedAccount.getUsername())
                .password(retrievedAccount.getPassword())
                .authorities(retrievedAccount.getRole().toString())
                .build();
    }
}
