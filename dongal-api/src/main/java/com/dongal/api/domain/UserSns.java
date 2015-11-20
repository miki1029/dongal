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
public class UserSns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "sns_id")
    private Sns sns;

    @Column(nullable = false, length = 45)
    private String snsValue;

    public UserSns(User user, Sns sns, String snsValue) {
        this.user = user;
        this.sns = sns;
        this.snsValue = snsValue;
    }
}
