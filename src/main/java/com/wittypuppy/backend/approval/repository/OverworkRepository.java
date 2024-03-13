package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.entity.ApprovalDoc;
import com.wittypuppy.backend.approval.entity.OnLeave;
import com.wittypuppy.backend.approval.entity.Overwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OverworkRepository extends JpaRepository<Overwork, Long> {
    Overwork findByApprovalDocCode(Long approvalDocCode);
}
