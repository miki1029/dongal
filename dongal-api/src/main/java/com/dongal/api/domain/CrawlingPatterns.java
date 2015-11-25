package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Freddi
 */
@Entity
@Table
@Data
public class CrawlingPatterns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 255)
    private String titlePattern;

    @Column(nullable = false, length = 255)
    private String urlPattern;

    @Column(nullable = false, length = 255)
    private String createdTimePattern;

    // insertable=true, updatable=true, nullable=true 기본값 삭제
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="category_id",/*insertable=true,
            updatable=true,nullable=true,*/unique=true)
    private Category category;
}
