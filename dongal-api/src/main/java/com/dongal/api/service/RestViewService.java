package com.dongal.api.service;

import com.dongal.api.response.ListData;
import com.dongal.api.response.SettingsData;

/**
 * @author miki
 */
public interface RestViewService {
    ListData home(Long homeIdx);
    ListData list(Long userIdx);
    ListData favorite(Long userIdx);
    SettingsData settings(Long userIdx);
}
