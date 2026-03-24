package com.example.localpos.modules.pos.service;

import com.example.localpos.modules.pos.dto.response.QrResponse;
import com.example.localpos.modules.pos.entity.Order;
import com.example.localpos.modules.pos.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class VietQrService {

    private final OrderRepository orderRepository;

    @Value("${vietqr.bankCode}")
    private String bankCode;

    @Value("${vietqr.accountNumber}")
    private String accountNumber;

    @Value("${vietqr.accountName}")
    private String accountName;

    public QrResponse generateQr(BigDecimal amount){

        String qrUrl =
                "https://img.vietqr.io/image/"
                        + bankCode + "-"
                        + accountNumber
                        + "-compact2.png"
                        + "?amount=" + amount
                        + "&addInfo= POS PAYMENT"
                        + "&accountName=" + accountName;

        return QrResponse.builder()
                .qrUrl(qrUrl)
                .bankAccount(accountNumber)
                .bankCode(bankCode)
                .build();
    }
}
