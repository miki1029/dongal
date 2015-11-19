package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Freddi
 */

@Entity
@Table(name = "crawling_last_seq")
@Data
public class CrawlingLastSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long lastSeq;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="category_id",insertable=true,
            updatable=true,nullable=true,unique=true)
    private Category category;
}
