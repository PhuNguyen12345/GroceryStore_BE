package com.example.localpos.modules.product.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;

    private String name;

    private Long parentId;
    private String parentName;

    private String slug;

    private String description;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;
}
