package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.entity.ApprovalAttached;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalAttachedRepository extends JpaRepository<ApprovalAttached, Long> {

    Optional<ApprovalAttached> findByApprovalDocCode(Long approvalDocCode);
}
