package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author miki
 */
@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(nullable = false)
    private boolean isDGUVerified;

    @OneToMany(mappedBy = "user")
    private List<UserSns> sns;

    @ManyToMany
    @JoinTable(name="user_category_settings",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="category_id"))
    private List<Category> categories;

    @ManyToMany
    @JoinTable(name="user_favorite",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="subscription_id"))
    private List<Subscription> favorites;
}
