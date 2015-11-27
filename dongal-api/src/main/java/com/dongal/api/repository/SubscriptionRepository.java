package com.dongal.api.repository;

import com.dongal.api.domain.Category;
import com.dongal.api.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author miki
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
//    @Query("select s from Subscription s inner join s.category c where c in ?1 and s.createdTime between ?2 and ?3")
    List<Subscription> findByCategoryInAndCreatedTimeBetween(List<Category> categories, Date startTime, Date endTime);

//    @Query("select s from Subscription s inner join s.category c where c in ?1")
    List<Subscription> findByCategoryIn(List<Category> categories);
}
