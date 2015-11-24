package com.dongal.api.service.impl;

import com.dongal.api.domain.Category;
import com.dongal.api.domain.User;
import com.dongal.api.repository.CategoryRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Freddi
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // TODO - 카테고리 ON/OFF API 맞추기
    @Override
    public void setUserCategories(Long userIdx, List<Long> categoryIdxes) {
        User user = userRepository.findOne(userIdx);
    }

    @Override
    public List<Category> findUserTopCategories(Long userIdx) {
        User user = userRepository.findOne(userIdx);
        List<Category> categories = user.getCategories();
        List<Category> topCategories = new ArrayList<>();
        for (Category category : categories) {
            // top category
            if (category.getTopCategory() == null && category.getMidCategory() == null) {
                topCategories.add(category);
            }
        }

        return topCategories;
    }
}
