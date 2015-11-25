package com.dongal.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Freddi
 */
@Entity
@Table(name = "admin_user")
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
}
