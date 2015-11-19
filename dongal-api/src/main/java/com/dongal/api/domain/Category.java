package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author miki
 */
@Entity
@Table
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "top_id")
    private Category topCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mid_id")
    private Category midCategory;

    @Column(nullable = false, length = 45)
    private String name;
}
