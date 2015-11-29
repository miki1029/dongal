package com.dongal.api.repository;

import com.dongal.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author miki
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
