package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.entity.ApprovalEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalEmployeeRepository extends JpaRepository<ApprovalEmployee, Long> {

    ApprovalEmployee findByEmployeeCode(Long employeeCode);
}
