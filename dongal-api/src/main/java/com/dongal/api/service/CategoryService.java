package com.dongal.api.service;

import java.util.List;

/**
 * @author Freddi
 */
public interface CategoryService {
    void setUserCategories(Long userIdx, List<Long> categoryIdxes);
    void addCategoryToUser(Long userIdx, Long categoryIdx);
    void delCategoryFromUser(Long userIdx, Long categoryIdx);
}
