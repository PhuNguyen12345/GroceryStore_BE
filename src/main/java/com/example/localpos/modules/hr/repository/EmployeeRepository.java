package com.example.localpos.modules.hr.repository;

import com.example.localpos.enums.EmployeeRole;
import com.example.localpos.modules.hr.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUsernameAndEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Find all active / inactive employees
    List<Employee> findByIsActive(Boolean isActive);

    // Find by role
    List<Employee> findByRole(EmployeeRole role);

    // Find active employees by role
    List<Employee> findByRoleAndIsActive(EmployeeRole role, Boolean isActive);

    // Case-insensitive full-name search
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    // Paginated search across username, fullName and email
    @Query("""
            SELECT e FROM Employee e
            WHERE (:keyword IS NULL
                OR LOWER(e.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(e.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(e.email)    LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:role     IS NULL OR e.role     = :role)
              AND (:isActive IS NULL OR e.isActive = :isActive)
            """)
    Page<Employee> searchEmployees(
            @Param("keyword") String keyword,
            @Param("role") EmployeeRole role,
            @Param("isActive") Boolean isActive,
            Pageable pageable
    );

    // Count by role
    long countByRole(EmployeeRole role);

    // Count active employees
    long countByIsActive(Boolean isActive);
}
