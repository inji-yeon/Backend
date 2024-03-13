package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.entity.ApprovalDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalDepartmentRepository extends JpaRepository<ApprovalDepartment, Long> {
    ApprovalDepartment findByDepartmentCode(Long departmentCode);
}
