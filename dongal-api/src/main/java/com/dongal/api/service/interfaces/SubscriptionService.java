package com.dongal.api.service.interfaces;

import com.dongal.api.domain.Subscription;

import java.util.Date;
import java.util.List;

/**
 * @author Freddi
 */
public interface SubscriptionService {
    List<Subscription> getUserSubscription(Long userIdx, Date startTime, Date endTime);
}
