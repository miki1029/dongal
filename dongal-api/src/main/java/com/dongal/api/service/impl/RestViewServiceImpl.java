package com.dongal.api.service.impl;

import com.dongal.api.domain.Category;
import com.dongal.api.domain.Subscription;
import com.dongal.api.domain.User;
import com.dongal.api.repository.CategoryRepository;
import com.dongal.api.repository.SubscriptionRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.response.ListData;
import com.dongal.api.response.SettingsData;
import com.dongal.api.service.RestViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author miki
 */
@Service
@Transactional
public class RestViewServiceImpl implements RestViewService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public ListData home(Long userIdx, Date lastLoginTime) {
        User user = userRepository.findOne(userIdx);

        List<Subscription> subscriptions = subscriptionRepository.findByCategoryInAndCrawlingTimeAfterOrderByCreatedTimeDesc(user.getCategories(), lastLoginTime);
        List<Subscription> homeSubscriptions = new ArrayList<>(user.getHomeCount());

        int homeCount = subscriptions.size();
        if (user.getHomeCount() < homeCount) homeCount = user.getHomeCount();

        for (int i=0; i<homeCount; i++) {
            homeSubscriptions.add(subscriptions.get(i));
        }

        ListData listData = new ListData(user, homeSubscriptions, user.getCategories(), lastLoginTime);

        return listData;
    }

    @Override
    public ListData list(Long userIdx, Date lastLoginTime) {
        User user = userRepository.findOne(userIdx);

        List<Subscription> subscriptions = new ArrayList<>();
        for (Category category : user.getCategories()) {
            subscriptions.addAll(category.getSubscriptions());
        }

        ListData listData = new ListData(user, subscriptions, user.getCategories(), lastLoginTime);

        return listData;
    }

    @Override
    public ListData favorite(Long userIdx, Date lastLoginTime) {
        User user = userRepository.findOne(userIdx);

        List<Subscription> subscriptions = user.getFavorites();

        ListData listData = new ListData(user, subscriptions, user.getCategories(), lastLoginTime);

        return listData;
    }

    @Override
    public SettingsData settings(Long userIdx) {
        User user = userRepository.findOne(userIdx);

        List<Category> officialCategories = categoryRepository.findByTopId(1L);
        List<Category> dyeonCategories = categoryRepository.findByTopId(2L);

        SettingsData settingsData = new SettingsData(user.getCategories(), officialCategories, dyeonCategories);

        return settingsData;
    }
}
