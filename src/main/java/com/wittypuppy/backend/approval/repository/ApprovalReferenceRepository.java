package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.entity.AdditionalApprovalLine;
import com.wittypuppy.backend.approval.entity.ApprovalReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalReferenceRepository extends JpaRepository<ApprovalReference, Long> {

    List<ApprovalReference> findByApprovalDocCode(Long approvalDocCode);
}
