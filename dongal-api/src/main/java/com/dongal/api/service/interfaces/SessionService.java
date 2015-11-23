package com.dongal.api.service.interfaces;

import com.dongal.api.domain.User;
import com.dongal.api.exception.WrongPasswordException;

/**
 * @author Freddi
 */
public interface SessionService {
    User login(String email, String password) throws WrongPasswordException;
    void logout(Long userIdx);
    User join(String email, String name, String password);
    void verifyDGU(Long userIdx);
}
