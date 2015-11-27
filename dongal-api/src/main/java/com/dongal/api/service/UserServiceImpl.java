package com.dongal.api.service;

import com.dongal.api.domain.Sns;
import com.dongal.api.service.interfaces.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Freddi
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean changePassword(int userIdx, String password) {
        return false;
    }

    @Override
    public boolean attachSNS(int userIdx, Sns sns) {
        return false;
    }

    @Override
    public boolean detachSNS(int userIdx, int snsIdx) {
        return false;
    }
}
