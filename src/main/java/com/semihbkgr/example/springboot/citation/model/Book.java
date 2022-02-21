package com.semihbkgr.example.springboot.citation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("books")
public class Book {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String isbn;

    private int author;

    private String name;

    private String explanation;

    @Column("release_year")
    private int releaseYear;

    @Column("page_count")
    private int pageCount;

    @CreatedBy
    @Column("created_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @EqualsAndHashCode.Exclude
    private int createdBy;

    @LastModifiedBy
    @Column("modified_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @EqualsAndHashCode.Exclude
    private int modifieddBy;

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
