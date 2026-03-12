package com.example.localpos.modules.pos.repository;

import com.example.localpos.modules.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
