package com.semihbkgr.example.springboot.citation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("authors")
public class Author {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String name;

    private String surname;

    private String biography;

    private String nation;

    @Column("birth_year")
    private int birthYear;

    @Column("death_year")
    private int deathYear;

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
