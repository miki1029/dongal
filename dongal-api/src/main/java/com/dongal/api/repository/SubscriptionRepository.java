package com.dongal.api.repository;

import com.dongal.api.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author miki
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
