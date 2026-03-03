package com.example.localpos.modules.inventory.dto.response;

import com.example.localpos.common.PageResponse;
import com.example.localpos.modules.inventory.entity.Supplier;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse extends PageResponse<Supplier> {
}
