package com.example.localpos.modules.crm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PromotionRequest {

    private Long id;

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    @Size(max = 200, message = "Tên khuyến mãi tối đa 200 ký tự")
    private String name;

    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String description;

    private Instant startDate;

    private Instant endDate;

    private Boolean isActive;

    @Size(max = 500, message = "Đường dẫn banner tối đa 500 ký tự")
    private String bannerUrl;
}