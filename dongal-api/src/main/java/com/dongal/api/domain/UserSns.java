package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author miki
 */
@Entity
@Table
@Data
public class UserSns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(optional = false) @JoinColumn(name = "sns_id")
    private Sns sns;

    @Column(nullable = false, length = 45)
    private String snsValue;
}
