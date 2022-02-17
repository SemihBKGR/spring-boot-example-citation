package com.semihbkgr.example.springboot.tale.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("tales")
public class Tale {

    @Id
    private Integer id;

    @CreatedBy
    private int author;

    private String title;

    private String description;

    @CreatedDate
    @Column("creation_time")
    private long creationTime;

    @LastModifiedDate
    @Column("modification_time")
    private long modificationTime;

}
