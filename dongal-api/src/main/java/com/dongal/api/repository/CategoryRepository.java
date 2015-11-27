package com.dongal.api.repository;

import com.dongal.api.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author miki
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
