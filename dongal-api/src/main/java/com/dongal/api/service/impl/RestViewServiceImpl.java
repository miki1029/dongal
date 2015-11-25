package com.dongal.api.service.impl;

import com.dongal.api.domain.Category;
import com.dongal.api.domain.Subscription;
import com.dongal.api.domain.User;
import com.dongal.api.repository.CategoryRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.response.ListData;
import com.dongal.api.response.SettingsData;
import com.dongal.api.service.RestViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public ListData home(Long userIdx) {
        User user = userRepository.findOne(userIdx);

        List<Subscription> subscriptions = new ArrayList<>();
        for (Category category : user.getCategories()) {
            subscriptions.addAll(category.getSubscriptions());
        }

        ListData listData = new ListData(user, subscriptions, user.getCategories());

        return listData;
    }

    @Override
    public SettingsData settings(Long userIdx) {
        User user = userRepository.findOne(userIdx);

        List<Category> topCategories = categoryRepository.findByTopCategoryIsNullAndMidCategoryIsNull();
        List<Category> midCategories = categoryRepository.findByTopCategoryNotNullAndMidCategoryIsNull();

        Category officialCategory = null;
        Category dyeonCategory = null;
        for (Category category : topCategories) {
            if (officialCategory == null && category.getName().equals("공지사항")) {
                officialCategory = category;
            } else if (dyeonCategory == null && category.getName().equals("디연")) {
                dyeonCategory = category;
            }
            if (officialCategory != null && dyeonCategory != null) break;
        }

        List<Category> officialCategories = categoryRepository.findByTopCategoryAndMidCategoryNotNull(officialCategory);
        List<Category> dyeonCategories = categoryRepository.findByTopCategoryAndMidCategoryNotNull(dyeonCategory);

        SettingsData settingsData = new SettingsData(topCategories, midCategories,
                user.getCategories(), officialCategories, dyeonCategories);

        return settingsData;
    }
}
