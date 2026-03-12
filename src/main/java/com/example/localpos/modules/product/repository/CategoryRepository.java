package com.example.localpos.modules.product.repository;

import com.example.localpos.modules.product.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    //tìm category theo slug
    Optional<Category> findBySlug(String slug);
    //kiểm tra slug đã tông tại chưa
    boolean existsBySlug(String slug);

    //Lấy toàn bộ category có phân trang + sort.
    Page<Category> findAllByOrderByNameAsc(Pageable pageable);
    //Tìm category theo tên (search admin).
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
    //Lọc category theo parentId (admin filter theo cha).
    Page<Category> findByParent_Id(Long id,Pageable pageable);
    Page<Category> findByIsActiveTrue(Pageable pageable);

    //Lấy root categories (không có parent).
    List<Category> findByParentIsNullOrderByNameAsc();
    //Lấy danh sách category con theo parentId.
    List<Category> findByParent_IdOrderByNameAsc(Long parentId);
}
