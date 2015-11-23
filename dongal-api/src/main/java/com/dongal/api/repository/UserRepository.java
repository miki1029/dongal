package com.dongal.api.repository;

import com.dongal.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author miki
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
