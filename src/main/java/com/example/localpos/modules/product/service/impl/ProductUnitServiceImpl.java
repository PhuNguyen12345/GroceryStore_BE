package com.example.localpos.modules.product.service.impl;

import com.example.localpos.modules.product.dto.request.ProductUnitCreateRequest;
import com.example.localpos.modules.product.dto.request.ProductUnitUpdateRequest;
import com.example.localpos.modules.product.dto.response.ProductUnitResponse;
import com.example.localpos.modules.product.entity.Product;
import com.example.localpos.modules.product.entity.ProductUnit;
import com.example.localpos.modules.product.mapper.ProductUnitMapper;
import com.example.localpos.modules.product.repository.ProductRepository;
import com.example.localpos.modules.product.repository.ProductUnitRepository;
import com.example.localpos.modules.product.service.ProductUnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductUnitServiceImpl implements ProductUnitService {
    private final ProductUnitRepository productUnitRepository;
    private final ProductRepository productRepository;
    private final ProductUnitMapper productUnitMapper;

    @Override
    public ProductUnitResponse addProductUnit(ProductUnitCreateRequest request) {
        ProductUnit productUnit = productUnitMapper.toEntity(request);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productUnit.setProduct(product);

        ProductUnit saved = productUnitRepository.save(productUnit);

        return productUnitMapper.toDto(saved);
    }

    @Override
    public ProductUnitResponse updateProductUnit(Long id, ProductUnitUpdateRequest request) {
        ProductUnit productUnit = productUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductUnit not found"));

        if (request.getUnitName() != null) {
            productUnit.setUnitName(request.getUnitName());
        }

        if (request.getConversionFactor() != null) {
            productUnit.setConversionFactor(request.getConversionFactor());
        }

        if (request.getBarcode() != null) {
            productUnit.setBarcode(request.getBarcode());
        }

        if (request.getSellingPrice() != null) {
            productUnit.setSellingPrice(request.getSellingPrice());
        }

        if (request.getReorderLevel() != null) {
            productUnit.setReorderLevel(request.getReorderLevel());
        }

        if (request.getIsBaseUnit() != null) {
            productUnit.setIsBaseUnit(request.getIsBaseUnit());
        }

        if (request.getIsActive() != null) {
            productUnit.setIsActive(request.getIsActive());
        }

        ProductUnit saved = productUnitRepository.save(productUnit);

        return productUnitMapper.toDto(saved);
    }

    @Override
    public List<ProductUnitResponse> getUnitsByProduct(Long productId) {
        List<ProductUnit> units = productUnitRepository.findByProduct_Id(productId);

        return units.stream()
                .map(productUnitMapper::toDto)
                .toList();
    }

    @Override
    public void deleteProductUnit(Long id) {
        ProductUnit unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductUnit not found"));

        unit.setIsActive(false);

        productUnitRepository.save(unit);
    }

    @Override
    public void restoreProductUnit(Long id) {
        ProductUnit unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductUnit not found"));

        unit.setIsActive(true);

        productUnitRepository.save(unit);
    }
}
