package com.dongal.api.repository;

import com.dongal.api.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author miki
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // find top categories
    List<Category> findByTopCategoryIsNullAndMidCategoryIsNull();
    // find mid categories
    List<Category> findByTopCategoryNotNullAndMidCategoryIsNull();
    // find bottom categories by mid
    List<Category> findByMidCategory(Category midCategory);
    // find bottom categories by top
    List<Category> findByTopCategoryAndMidCategoryNotNull(Category topCategory);
}
