package com.dongal.api.service;

import com.dongal.api.repository.SubscriptionRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.interfaces.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Freddi
 */

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public boolean addFavoriteToUser(int userIdx, int subscriptionIdx) {
        return false;
    }

    public boolean delFavoriteFromUser(int userIdx, int subscriptionIdx) {
        return false;
    }

}
