package com.dongal.api.service.interfaces;

/**
 * @author Freddi
 */
public interface FavoriteService {
    boolean addFavoriteToUser(int userIdx, int subscriptionIdx);
    boolean delFavoriteFromUser(int userIdx, int subscriptionIdx);
}
