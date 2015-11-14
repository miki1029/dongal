package com.dongal.api.repository;

import com.dongal.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by miki on 2015. 11. 14..
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
