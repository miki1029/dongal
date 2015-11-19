package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Freddi
 */
@Entity
@Table(name = "admin_push_message")
@Data
public class AdminPushMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 45)
    private String message;
}
