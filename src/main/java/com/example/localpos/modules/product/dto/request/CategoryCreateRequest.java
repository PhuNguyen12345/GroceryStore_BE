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
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    private String name;

    private Long parentId;

    @Size(max = 150, message = "Slug không được vượt quá 150 ký tự")
    @Pattern(
            regexp = "^[a-z0-9-]*$",
            message = "Slug chỉ được chứa chữ thường, số và dấu gạch nối"
    )
    private String slug;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;

    private Boolean isActive;
}
