package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.Employee.entity.LoginEmployee;
import com.wittypuppy.backend.approval.entity.AdditionalApprovalLine;
import com.wittypuppy.backend.approval.entity.ApprovalDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApprovalDocRepository extends JpaRepository<ApprovalDoc, Long> {
    List<ApprovalDoc> findByEmployeeCodeOrderByApprovalDocCodeDesc(LoginEmployee loginEmployee);

    @Query(value =
            "SELECT " +
                    "approval_document_code " +
                    "FROM tbl_approval_line a " +
                    "WHERE employee_code = :employeeCode " +
                    "AND approval_process_status = '대기' " +
                    "AND approval_process_order = (" +
                    "SELECT " +
                    "MIN(approval_process_order) " +
                    "FROM tbl_approval_line b " +
                    "WHERE " +
                    "a.approval_document_code = b.approval_document_code " +
                    "AND a.approval_process_status = b.approval_process_status)",
            nativeQuery = true)
    List<Long> inboxDocListByEmployeeCode(Long employeeCode);

    ApprovalDoc findByApprovalDocCode(Long approvalDocCode);

    List<ApprovalDoc> findByEmployeeCodeAndWhetherSavingApproval(LoginEmployee loginEmployee, String whetherSavingApproval);

    @Query(value =
            "SELECT approval_document_code " +
                    "FROM tbl_approval_line " +
                    "WHERE employee_code = :employeeCode " +
                    "AND approval_process_status not in ('기안', '회수', '대기')",
            nativeQuery = true)
    List<Long> inboxFinishedListbyEmployeeCode(Long employeeCode);
}