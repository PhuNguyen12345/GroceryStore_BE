package com.example.localpos.modules.pos.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QrResponse {

    private Long orderId;

    private String qrUrl;

    private String bankAccount;

    private String bankCode;

    private String amount;

}