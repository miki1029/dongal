package com.dongal.api.service.impl;

import com.dongal.api.domain.Category;
import com.dongal.api.domain.User;
import com.dongal.api.repository.CategoryRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Freddi
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void setUserCategories(Long userIdx, List<Long> categoryIdxes) {
        User user = userRepository.findOne(userIdx);

        user.getCategories().clear();

        for (Long categoryIdx : categoryIdxes) {
            Category category = categoryRepository.findOne(categoryIdx);
            user.getCategories().add(category);
        }

        userRepository.save(user);
    }
}
