package com.dongal.api.service;

import com.dongal.api.domain.User;
import com.dongal.api.repository.CategoryRepository;
import com.dongal.api.repository.UserRepository;
import com.dongal.api.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void setUserCategories(Long userIdx, List<Long> categoryIdxes) {
        User user = userRepository.findOne(userIdx);
    }

    // TODO - 카테고리 ON/OFF API 맞추기
}
