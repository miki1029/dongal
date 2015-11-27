package com.dongal.api.service;

import com.dongal.api.domain.User;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Freddi
 */
public class SessionServiceImpl implements SessionService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public boolean logout(int userIdx) {
        return false;
    }

    @Override
    public User join(String email, String password) {
        return null;
    }

    @Override
    public boolean verifyDGU(int userIdx) {
        return false;
    }
}
