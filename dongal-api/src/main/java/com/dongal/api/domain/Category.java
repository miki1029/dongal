package com.dongal.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author miki
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "top_id")
    private Category topCategory;

    @ManyToOne
    @JoinColumn(name = "mid_id")
    private Category midCategory;

    @Column(nullable = false, length = 45)
    private String name;

    public Category(String name) {
        this.name = name;
        topCategory = null;
        midCategory = null;
    }

    public Category(String name, Category superCategory) {
        this.name = name;
        // superCategory is topCategory
        if(superCategory.topCategory == null && superCategory.midCategory == null) {
            this.topCategory = superCategory;
            this.midCategory = null;
        }
        // superCategory is midCategory
        else if(superCategory.topCategory != null && superCategory.midCategory == null) {
            this.topCategory = superCategory.topCategory;
            this.midCategory = superCategory;
        }
        // bad input
        else {
            throw new RuntimeException("Bad input : " + superCategory);
        }
    }
}
