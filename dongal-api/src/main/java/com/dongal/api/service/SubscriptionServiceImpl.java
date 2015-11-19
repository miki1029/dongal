package com.dongal.api.service;

import com.dongal.api.domain.Subscription;
import com.dongal.api.repository.SubscriptionRepository;
import com.dongal.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Freddi
 */
@Service
public class SubscriptionServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getUserSubscription(int userIdx, Date startTime, Date endTime) {
        return null;
    }
}
