package com.example.localpos.modules.product.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private Long categoryId;
    private String categoryName;

    private Long brandId;
    private String brandName;

    private String imageUrl;

    private Boolean isActive;

    private Instant createdAt;
}
