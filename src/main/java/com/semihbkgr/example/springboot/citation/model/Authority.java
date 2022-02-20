package com.semihbkgr.example.springboot.citation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("authorities")
public class Authority {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String name;

    private String explanation;

    @CreatedBy
    @Column("created_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int createdBy;

    @LastModifiedBy
    @Column("modified_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int modifieddBy;

    @CreatedDate
    @Column("created_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long createdAt;

    @LastModifiedDate
    @Column("modified_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long modifiedAt;

    public boolean isRole() {
        return name.startsWith("ROLE_");
    }

}
