package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by miki on 2015. 11. 14..
 */
@Entity
@Table
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String email;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false) @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Column(nullable = false)
    private boolean isDguVerified;

    @OneToMany(mappedBy = "user")
    private List<UserSns> snses;
    @OneToMany(mappedBy = "user")
    private List<Category> categories;
    @OneToMany(mappedBy = "user")
    private List<Subscription> favorites;
}
