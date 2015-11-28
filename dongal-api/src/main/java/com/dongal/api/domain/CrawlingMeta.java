package com.dongal.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Freddi
 */

@Entity
@Table
@Data
@NoArgsConstructor
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

    public CrawlingMeta(String createdTimePattern, String titlePattern, String url,
                        Category category, String secretPattern, String categoryPattern, Long lastSeq, String urlPattern) {
        this.createdTimePattern = createdTimePattern;
        this.titlePattern = titlePattern;
        this.url = url;
        this.category = category;
        this.secretPattern = secretPattern;
        this.categoryPattern = categoryPattern;
        this.lastSeq = lastSeq;
        this.urlPattern = urlPattern;
    }
}
