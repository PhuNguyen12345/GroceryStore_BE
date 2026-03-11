package com.example.localpos.modules.product.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryTreeResponse {
    private Long id;
    private String name;
    private String slug;
    private Boolean isActive;

    @Builder.Default
    private List<CategoryTreeResponse> children = new ArrayList<>();
}
