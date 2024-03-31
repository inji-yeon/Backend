package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.entity.ApprovalDoc;
import com.wittypuppy.backend.approval.entity.OnLeave;
import com.wittypuppy.backend.approval.entity.SoftwareUse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoftwareUseRepository extends JpaRepository<SoftwareUse, Long> {
    SoftwareUse findByApprovalDocCode(Long approvalDocCode);
}
