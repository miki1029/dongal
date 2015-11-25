package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Freddi
 */

@Entity
@Table
@Data
public class CrawlingLastSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long lastSeq;

    // insertable=true, updatable=true, nullable=true 기본값 삭제
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="category_id",/*insertable=true,
            updatable=true,nullable=true,*/unique=true)
    private Category category;
}
