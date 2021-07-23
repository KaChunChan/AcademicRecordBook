package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.EndUser;
import com.kachunchan.academicrecordbook.repository.EndUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    private final EndUserRepository endUserRepository;

    public LoginService(EndUserRepository endUserRepository) {
        this.endUserRepository = endUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EndUser retrievedEndUser = endUserRepository.findByUsername(username);
        return User
                .withUsername(retrievedEndUser.getUsername())
                .password(retrievedEndUser.getPassword())
                .authorities(retrievedEndUser.getRole().getCode())
                .build();
    }
}
