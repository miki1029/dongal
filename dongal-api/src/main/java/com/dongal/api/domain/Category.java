package com.dongal.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions = new ArrayList<>();

    public Category(String name) {
        this.name = name;
        topCategory = null;
        midCategory = null;
    }

    public Category(String name, Category superCategory) {
        this.name = name;
        // superCategory is topCategory
        if(superCategory.getCategoryType().equals(CategoryEnum.TOP)) {
            this.topCategory = superCategory;
            this.midCategory = null;
        }
        // superCategory is midCategory
        else if(superCategory.getCategoryType().equals(CategoryEnum.MID)) {
            this.topCategory = superCategory.topCategory;
            this.midCategory = superCategory;
        }
        // bad input
        else {
            throw new RuntimeException("Bad input : " + superCategory);
        }
    }

    public CategoryEnum getCategoryType() {
        if(topCategory == null && midCategory == null) return CategoryEnum.TOP;
        else if(topCategory !=null && midCategory == null) return CategoryEnum.MID;
        else return CategoryEnum.BOTTOM;
    }
}
