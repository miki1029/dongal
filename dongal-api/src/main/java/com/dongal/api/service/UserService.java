package com.dongal.api.service;

/**
 * @author Freddi
 */
public interface UserService {
    void changePassword(Long userIdx, String password);
    void attachSNS(Long userIdx, Long snsIdx, String snsValue);
    void detachSNS(Long userIdx, Long snsIdx);
}
