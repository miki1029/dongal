package com.dongal.api.service.impl;

import com.dongal.api.domain.Sns;
import com.dongal.api.domain.User;
import com.dongal.api.domain.UserSns;
import com.dongal.api.repository.SnsRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Freddi
 */
@Service
//@Transactional
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
    public void attachSNS(Long userIdx, Long snsIdx, String snsValue) {
        User user = userRepository.findOne(userIdx);
        Sns sns = snsRepository.findOne(snsIdx);

        UserSns userSns = null;
        for (UserSns us : user.getSns()) {
            if (us.getSns().equals(sns)) {
                userSns = us;
                break;
            }
        }

        if(userSns == null) {
            user.getSns().add(new UserSns(user, sns, snsValue));
        } else {
            userSns.setSnsValue(snsValue);
        }

        userRepository.save(user);
    }

    @Override
    public void detachSNS(Long userIdx, Long snsIdx) {
        User user = userRepository.findOne(userIdx);
        Sns sns = snsRepository.findOne(snsIdx);

        UserSns userSns = null;
        for (UserSns us : user.getSns()) {
            if (us.getSns().equals(sns)) {
                userSns = us;
                break;
            }
        }

        if (userSns != null) {
            user.getSns().remove(userSns);
        }

        userRepository.save(user);
    }
}
