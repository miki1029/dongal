package com.dongal.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author miki
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 1024)
    private String title;

    @Column(nullable = false, length = 2048)
    private String url;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public Subscription(Category category, String title, String url, Date createdTime) {
        this.category = category;
        this.title = title;
        this.url = url;
        this.createdTime = createdTime;
    }
}
