package com.semihbkgr.example.springboot.citation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

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

    @ReadOnlyProperty
    @JsonIgnore
    private String authorities;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<String> authoritySet;

    @CreatedDate
    @Column("created_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long createdAt;

    @LastModifiedDate
    @Column("modified_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long modifiedAt;

}
