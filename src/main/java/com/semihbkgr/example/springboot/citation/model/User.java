package com.semihbkgr.example.springboot.citation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("users")
public class User {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    @CreatedDate
    @Column("creation_time")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long creationTime;

    @LastModifiedDate
    @Column("modification_time")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long modificationTime;

}
