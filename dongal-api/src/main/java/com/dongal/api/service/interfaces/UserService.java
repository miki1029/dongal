package com.dongal.api.service.interfaces;

import com.dongal.api.domain.Sns;

/**
 * @author Freddi
 */
public interface UserService {
    void changePassword(Long userIdx, String password);
    void attachSNS(Long userIdx, Sns sns, String snsValue);
    void detachSNS(Long userIdx, Long snsIdx);
}
