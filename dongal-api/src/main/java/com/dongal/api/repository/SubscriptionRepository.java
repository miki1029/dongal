package com.dongal.api.repository;

import com.dongal.api.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by miki on 2015. 11. 14..
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
