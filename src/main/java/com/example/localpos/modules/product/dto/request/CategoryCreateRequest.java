package com.example.localpos.modules.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequest {
    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private Long parentId;

    @Size(max = 150, message = "Slug must not exceed 150 characters")
    @Pattern(
            regexp = "^[a-z0-9-]*$",
            message = "Slug must contain only lowercase letters, numbers and hyphens"
    )
    private String slug;

    @Size(max = 1000, message = "Description is too long")
    private String description;

    private Boolean isActive;
}
