package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author miki
 */
@Entity
@Table
@Data
public class Sns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 45)
    private String name;
}
