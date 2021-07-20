package com.kachunchan.academicrecordbook.service;

import com.kachunchan.academicrecordbook.domain.EndUser;
import com.kachunchan.academicrecordbook.repository.EndUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndUserServiceImpl implements EndUserService {

    private final EndUserRepository endUserRepository;

    public EndUserServiceImpl(EndUserRepository endUserRepository) {
        this.endUserRepository = endUserRepository;
    }

    @Override
    public EndUser addEndUser(EndUser endUser) {
        if (endUserRepository.findByUsername(endUser.getUsername()) != null) {
            return null;
        }
        return endUserRepository.save(endUser);
    }

    @Override
    public EndUser getEndUser(long id) {
        if (endUserRepository.existsById(id)) {
            return endUserRepository.getOne(id);
        }
        return null;
    }

    @Override
    public EndUser getEndUser(String username) {
        return endUserRepository.findByUsername(username);
    }

    @Override
    public List<EndUser> getAllEndUsers() {
        return endUserRepository.findAll();
    }

    @Override
    public EndUser editEndUser(EndUser endUser) {
        return endUserRepository.save(endUser);
    }

    @Override
    public void deleteEndUser(long id) {
        if (endUserRepository.existsById(id)) {
            endUserRepository.deleteById(id);
        }
    }
}
