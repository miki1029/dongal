package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by miki on 2015. 11. 14..
 */
@Entity
@Table
@Data
public class UserSns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(optional = false) @JoinColumn(name = "sns_id")
    private Sns sns;

    @Column(nullable = false, length = 45)
    private String snsValue;
}
