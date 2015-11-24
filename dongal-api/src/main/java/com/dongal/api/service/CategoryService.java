package com.dongal.api.service;

import com.dongal.api.domain.Category;

import java.util.List;

/**
 * @author Freddi
 */
public interface CategoryService {
    void setUserCategories(Long userIdx, List<Long> categoryIdxes);

    List<Category> findUserTopCategories(Long userIdx);
}
