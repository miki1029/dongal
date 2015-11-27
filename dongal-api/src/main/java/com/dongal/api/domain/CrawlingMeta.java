package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Freddi
 */

@Entity
@Table
@Data
public class CrawlingMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 255)
    private String createdTimePattern;

    @Column(nullable = false, length = 500)
    private String titlePattern;

    @Column(nullable = false, length = 255)
    private String urlPattern;

    @OneToOne(optional = true)
    @JoinColumn(name="category_id", unique=true)
    private Category category;

    @Column(length = 255)
    private String secretPattern;

    @Column(length = 255)
    private String categoryPattern;

    @Column
    private Long lastSeq;

    @Column(length = 255)
    private String url;
}
