package com.dongal.api.service.interfaces;

import java.util.List;

/**
 * @author Freddi
 */
public interface CategoryService {
    void setUserCategories(Long userIdx, List<Long> categoryIdxes);
}
