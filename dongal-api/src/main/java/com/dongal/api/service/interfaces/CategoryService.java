package com.dongal.api.service.interfaces;

import java.util.List;

/**
 * @author Freddi
 */
public interface CategoryService {
    boolean setUserCategories(int userIdx, List<Integer> categoryIdxes);
}
