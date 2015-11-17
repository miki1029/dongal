package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author miki
 */
@Entity
@Table
@Data
public class Subscription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "category_id")
    private Category category;
    @Column(nullable = false, length = 1024)
    private String title;
    @Column(nullable = false, length = 2048)
    private String url;
    @Column(nullable = false) @Temporal(TemporalType.TIMESTAMP)
    private Date cratedTime;
}
