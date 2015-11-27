package com.dongal.api.service.impl;

import com.dongal.api.domain.Subscription;
import com.dongal.api.domain.User;
import com.dongal.api.repository.SubscriptionRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Freddi
 */
@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public void addFavoriteToUser(Long userIdx, Long subscriptionIdx) {
        User user = userRepository.findOne(userIdx);
        Subscription favorite = subscriptionRepository.findOne(subscriptionIdx);

        user.getFavorites().add(favorite);

        userRepository.save(user);
    }

    @Override
    public void delFavoriteFromUser(Long userIdx, Long subscriptionIdx) {
        User user = userRepository.findOne(userIdx);
        Subscription favorite = subscriptionRepository.findOne(subscriptionIdx);

        user.getFavorites().remove(favorite);

        userRepository.save(user);
    }

}
