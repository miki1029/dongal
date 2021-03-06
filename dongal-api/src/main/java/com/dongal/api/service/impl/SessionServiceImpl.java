package com.dongal.api.service.impl;

import com.dongal.api.domain.User;
import com.dongal.api.exception.AlreadyExistUserException;
import com.dongal.api.exception.WrongPasswordException;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Freddi
 */
@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String email, String password) throws WrongPasswordException {
        List<User> users = userRepository.findByEmail(email);
        User user = users.get(0);

        if (!user.getPassword().equals(password)) {
            throw new WrongPasswordException();
        }

        return user;
    }

    @Override
    public void logout(Long userIdx) {
        User user = userRepository.findOne(userIdx);
    }

    @Override
    public User join(String email, String password, String name, String deviceKey) throws AlreadyExistUserException {
        List<User> existUsers = userRepository.findByEmail(email);
        if (existUsers.size() > 0) {
            throw new AlreadyExistUserException();
        }
        User user = new User(email, password, name, new Date(), false);
        user.setDeviceKey(deviceKey);
        userRepository.save(user);
        return user;
    }

    @Override
    public void verifyDGU(Long userIdx) {
        User user = userRepository.findOne(userIdx);
        user.setDguVerified(true);
        userRepository.save(user);
    }

    @Override
    public void updateLoginTime(Long userIdx) {
        User user = userRepository.findOne(userIdx);
        user.setLastLoginTime(new Date());
        userRepository.save(user);
    }
}
