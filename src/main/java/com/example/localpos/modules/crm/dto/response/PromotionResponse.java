package com.example.localpos.modules.crm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PromotionResponse {

    private Long id;
    private String name;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private Boolean isActive;
    private String bannerUrl;
}