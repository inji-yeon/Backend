package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.entity.ApprovalDoc;
import com.wittypuppy.backend.approval.entity.OnLeave;
import com.wittypuppy.backend.approval.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {
    WorkType findByApprovalDocCode(Long approvalDocCode);
}
