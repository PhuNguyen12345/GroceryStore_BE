package com.example.localpos.modules.product.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponse {

    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;
}
