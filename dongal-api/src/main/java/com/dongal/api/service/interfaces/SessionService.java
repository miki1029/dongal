package com.dongal.api.service.interfaces;

import com.dongal.api.domain.User;

/**
 * @author Freddi
 */
public interface SessionService {
    User login(String email, String password);
    boolean logout(int userIdx);
    User join(String email, String password);
    boolean verifyDGU(int userIdx);
}
