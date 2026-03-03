package com.example.localpos.modules.inventory.dto.supplier.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest {
    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    @Size(max = 150, message = "Tên nhà cung cấp không được vượt quá 150 ký tự.")
    private String name;
    @Size(max = 100, message = "Tên người liên hệ không được vượt quá 100 ký tự.")
    private String contactPerson;
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;
    private String address;
}
