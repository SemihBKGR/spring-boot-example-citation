package com.semihbkgr.example.springboot.citation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("users")
public class User {

    @Id
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    @CreatedDate
    @Column("creation_time")
    private long creationTime;

}
