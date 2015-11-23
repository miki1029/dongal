package com.dongal.api.service.interfaces;

/**
 * @author Freddi
 */
public interface FavoriteService {
    void addFavoriteToUser(Long userIdx, Long subscriptionIdx);
    void delFavoriteFromUser(Long userIdx, Long subscriptionIdx);
}
