package com.dongal.api.repository;

import com.dongal.api.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by miki on 2015. 11. 14..
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
