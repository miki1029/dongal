package com.dongal.api.service;

import com.dongal.api.domain.Sns;
import com.dongal.api.domain.User;
import com.dongal.api.repository.SnsRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Freddi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SnsRepository snsRepository;

    @Override
    public void changePassword(Long userIdx, String password) {
        User user = userRepository.findOne(userIdx);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void attachSNS(Long userIdx, Sns sns, String snsValue) {
        User user = userRepository.findOne(userIdx);
        user.attachSns(sns, snsValue);
        userRepository.save(user);
    }

    @Override
    public void detachSNS(Long userIdx, Long snsIdx) {
        User user = userRepository.findOne(userIdx);
        Sns sns = snsRepository.findOne(snsIdx);
        user.detachSns(sns);
        userRepository.save(user);
    }
}
