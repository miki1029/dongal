package com.dongal.api.repository;

import com.dongal.api.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author miki
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
