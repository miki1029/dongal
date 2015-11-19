package com.dongal.api.service.interfaces;

import java.util.List;

/**
 * @author Freddi
 */
public interface FavoriteService {
    public boolean setUserCategories(int userIdx, List<Integer> categoryIdxes);
}
