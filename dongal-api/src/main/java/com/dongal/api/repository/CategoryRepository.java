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
    List<Category> findByTopId(Long topId);
}
