package com.semihbkgr.example.springboot.citation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collections;
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

    @EqualsAndHashCode.Exclude
    private String password;

    private String firstname;

    private String lastname;

    @ReadOnlyProperty
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private String authorities;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @EqualsAndHashCode.Exclude
    private Set<String> authoritySet = Collections.emptySet();

    @CreatedDate
    @Column("created_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @EqualsAndHashCode.Exclude
    private long createdAt;

    @LastModifiedDate
    @Column("modified_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @EqualsAndHashCode.Exclude
    private long modifiedAt;

}
