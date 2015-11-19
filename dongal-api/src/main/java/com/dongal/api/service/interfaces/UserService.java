package com.dongal.api.service.interfaces;

import com.dongal.api.domain.Sns;

/**
 * @author Freddi
 */
public interface UserService {
    boolean changePassword(int userIdx, String password);
    boolean attachSNS(int userIdx, Sns sns);
    boolean detachSNS(int userIdx, int snsIdx);
}
