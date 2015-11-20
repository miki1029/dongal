package com.dongal.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author miki
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(nullable = false)
    private boolean isDguVerified; // 변수명 하이버네이트 이름 규칙 때문에 고침

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSns> sns = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="user_category_settings",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="category_id"))
    private List<Category> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="user_favorite",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="subscription_id"))
    private List<Subscription> favorites = new ArrayList<>();

    public User(String email, String name, String password, Date createdTime, boolean isDguVerified) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdTime = createdTime;
        this.isDguVerified = isDguVerified;
    }
}
