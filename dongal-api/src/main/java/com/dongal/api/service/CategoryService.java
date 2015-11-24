package com.dongal.api.service;

import java.util.List;

/**
 * @author Freddi
 */
public interface CategoryService {
    void setUserCategories(Long userIdx, List<Long> categoryIdxes);
}
