package com.dongal.api.repository;

import com.dongal.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author miki
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
