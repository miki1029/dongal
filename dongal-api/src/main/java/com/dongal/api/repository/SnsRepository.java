package com.dongal.api.repository;

import com.dongal.api.domain.Sns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author miki
 */
@Repository
public interface SnsRepository extends JpaRepository<Sns, Long> {
}
