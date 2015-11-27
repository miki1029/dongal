package com.dongal.api.repository;

import com.dongal.api.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author miki
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
