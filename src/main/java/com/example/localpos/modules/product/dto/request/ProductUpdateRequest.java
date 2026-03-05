package com.example.localpos.modules.product.dto.request;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    @Size(max = 200)
    private String name;

    private String description;

    private Long categoryId;

    private Long brandId;

    @Size(max = 500)
    private String imageUrl;

    private Boolean isActive;
}
