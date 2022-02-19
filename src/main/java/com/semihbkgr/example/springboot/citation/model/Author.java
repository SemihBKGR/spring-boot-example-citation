package com.semihbkgr.example.springboot.citation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
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
    @Column("creation_time")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long creationTime;

    @LastModifiedDate
    @Column("modification_time")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long modificationTime;

}
