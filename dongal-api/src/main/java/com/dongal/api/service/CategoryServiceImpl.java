package com.dongal.api.service;

import com.dongal.api.repository.CategoryRepository;
import com.dongal.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Freddi
 */
@Service
public class CategoryServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean setUserCategories(int userIdx, List<Integer> categoryIdxes) {
        return false;
    }
}
