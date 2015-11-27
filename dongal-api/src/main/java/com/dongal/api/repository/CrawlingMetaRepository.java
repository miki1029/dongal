package com.dongal.api.repository;

import com.dongal.api.domain.CrawlingMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author miki
 */
@Repository
public interface CrawlingMetaRepository extends JpaRepository<CrawlingMeta, Long> {
}
