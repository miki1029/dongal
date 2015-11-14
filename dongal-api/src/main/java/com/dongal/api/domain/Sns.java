package com.dongal.api.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by miki on 2015. 11. 14..
 */
@Entity
@Table
@Data
public class Sns {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String name;
}
