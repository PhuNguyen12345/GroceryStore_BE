package com.example.localpos.modules.crm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private Long id;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
            regexp = "^(0(3|5|7|8|9)\\d{8}|(\\+84|84)(3|5|7|8|9)\\d{8})$",
            message = "Số điện thoại phải đúng định dạng di động Việt Nam"
    )
    private String phone;

    @Size(max = 100, message = "Họ và tên tối đa 100 ký tự")
    private String fullName;

    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email tối đa 100 ký tự")
    private String email;

    @Size(max = 500, message = "Địa chỉ tối đa 500 ký tự")
    private String address;}