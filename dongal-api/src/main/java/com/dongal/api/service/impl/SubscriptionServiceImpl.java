package com.dongal.api.service.impl;

import com.dongal.api.domain.Subscription;
import com.dongal.api.domain.User;
import com.dongal.api.repository.SubscriptionRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Freddi
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> getUserSubscription(Long userIdx, Date startTime, Date endTime) {
        User user = userRepository.findOne(userIdx);
        List<Subscription> subscriptions = subscriptionRepository.findByCategoryInAndCreatedTimeBetween(
                user.getCategories(), startTime, endTime);

        return subscriptions;
    }
}
