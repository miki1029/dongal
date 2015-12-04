package com.dongal.api.service;

import com.dongal.api.response.ListData;
import com.dongal.api.response.SettingsData;

import java.util.Date;

/**
 * @author miki
 */
public interface RestViewService {
    ListData home(Long homeIdx, Date lastLoginTime);
    ListData list(Long userIdx, Date lastLoginTime);
    ListData favorite(Long userIdx, Date lastLoginTime);
    SettingsData settings(Long userIdx);
}
