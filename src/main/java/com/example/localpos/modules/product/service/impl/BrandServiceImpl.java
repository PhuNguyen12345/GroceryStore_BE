package com.example.localpos.modules.product.service.impl;

import com.example.localpos.common.response.PageResponse;
import com.example.localpos.modules.product.dto.request.BrandCreateRequest;
import com.example.localpos.modules.product.dto.request.BrandUpdateRequest;
import com.example.localpos.modules.product.dto.response.BrandResponse;
import com.example.localpos.modules.product.entity.Brand;
import com.example.localpos.modules.product.mapper.BrandCreateRequestMapper;
import com.example.localpos.modules.product.mapper.BrandResponseMapper;
import com.example.localpos.modules.product.repository.BrandRepository;
import com.example.localpos.modules.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandCreateRequestMapper brandCreateRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public PageResponse<BrandResponse> getAllBrands(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Brand> brandPage = brandRepository.findAllByOrderByNameAsc(pageable);

        List<BrandResponse> responseList = brandPage.getContent().stream()
                .map(brandResponseMapper::toDto)
                .toList();

        return PageResponse.<BrandResponse>builder()
                .content(responseList)
                .page(brandPage.getNumber())
                .size(brandPage.getSize())
                .totalElements(brandPage.getTotalElements())
                .totalPages(brandPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<BrandResponse> findBrandsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Brand> brandPage = brandRepository.findByNameContainingIgnoreCase(name, pageable);

        List<BrandResponse> responseList = brandPage.getContent().stream()
                .map(brandResponseMapper::toDto)
                .toList();

        return PageResponse.<BrandResponse>builder()
                .content(responseList)
                .page(brandPage.getNumber())
                .size(brandPage.getSize())
                .totalElements(brandPage.getTotalElements())
                .totalPages(brandPage.getTotalPages())
                .build();
    }

    @Override
    public PageResponse<BrandResponse> getActiveBrands(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Brand> brandPage = brandRepository.findByIsActiveTrue(pageable);

        List<BrandResponse> responseList = brandPage.getContent().stream()
                .map(brandResponseMapper::toDto)
                .toList();

        return PageResponse.<BrandResponse>builder()
                .content(responseList)
                .page(brandPage.getNumber())
                .size(brandPage.getSize())
                .totalElements(brandPage.getTotalElements())
                .totalPages(brandPage.getTotalPages())
                .build();
    }

    @Override
    public BrandResponse addBrand(BrandCreateRequest request) {
        Brand brand = brandCreateRequestMapper.toEntity(request);

        // kiểm tra tên có bị trùng không
        if (request.getName() != null && brandRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Tên thương hiệu đã tồn tại");
        }

        // mặc định isActive
        if (brand.getIsActive() == null) brand.setIsActive(true);

        Brand saved = brandRepository.save(brand);
        return brandResponseMapper.toDto(saved);
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));

        if (request.getName() != null) {
            // kiểm tra trùng tên với thương hiệu khác
            if (brandRepository.existsByNameIgnoreCase(request.getName())
                    && !brand.getName().equalsIgnoreCase(request.getName())) {
                throw new IllegalArgumentException("Tên thương hiệu đã tồn tại");
            }
            brand.setName(request.getName());
        }

        if (request.getDescription() != null) {
            brand.setDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            brand.setIsActive(request.getIsActive());
        }

        Brand saved = brandRepository.save(brand);
        return brandResponseMapper.toDto(saved);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));

        if (Boolean.FALSE.equals(brand.getIsActive())) return;

        brand.setIsActive(false);
        brandRepository.save(brand);
    }

    @Override
    public void restoreBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu"));

        brand.setIsActive(true);
        brandRepository.save(brand);
    }
}
