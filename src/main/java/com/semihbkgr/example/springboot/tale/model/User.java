package com.semihbkgr.example.springboot.tale.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class User {

    @Id
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    @Column("creation_time")
    private long creationTime;

}
