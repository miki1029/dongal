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

    @Column(nullable = false)
    private Long topId;

    @Column(nullable = false, length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions = new ArrayList<>();

    public Category(String name, CategoryEnum categoryEnum) {
        this.name = name;
        switch (categoryEnum) {
            case DONGGUK:
                this.topId = 1L;
                break;
            case DYEON:
                this.topId = 2L;
                break;
        }
    }

    public CategoryEnum getCategoryType() {
        if (topId.equals(1L)) {
            return CategoryEnum.DONGGUK;
        } else if (topId.equals(2L)) {
            return CategoryEnum.DYEON;
        } else {
            throw new RuntimeException("잘못된 topId");
        }
    }
}
