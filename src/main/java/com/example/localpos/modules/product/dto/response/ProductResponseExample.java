package com.example.localpos.modules.product.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseExample {
    private Long id;
    private String name;
    private Long brandId;
    private String brandName;
    private String categoryName;
}
