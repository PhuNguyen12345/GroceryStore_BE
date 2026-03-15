package com.example.localpos.modules.pos.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckoutResponse {

    private String status;

    private String qrUrl;

    private String message;

}
