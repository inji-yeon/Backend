package com.wittypuppy.backend.approval.repository;

import com.wittypuppy.backend.approval.dto.AdditionalApprovalLineDTO;
import com.wittypuppy.backend.approval.entity.AdditionalApprovalLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface AdditionalApprovalLineRepository extends JpaRepository<AdditionalApprovalLine, Long> {
    @Query(value =
            "SELECT " +
                    "employee_code " +
                    "FROM tbl_approval_line " +
                    "WHERE approval_document_code = :approvalDocCode " +
                    "AND approval_process_status = '대기' " +
                    "ORDER BY approval_line_code ASC " +
                    "LIMIT 1",
            nativeQuery = true)
    Long approvalSubjectEmployeeCode(Long approvalDocCode);


    AdditionalApprovalLine findByApprovalDocCodeAndEmployeeCode(Long approvalDocCode, Long employeeCode);

    @Query(value =
            "SELECT " +
                    "approval_line_code " +
                    "FROM tbl_approval_line " +
                    "WHERE approval_document_code = :approvalDocCode " +
                    "AND approval_process_status = '대기'",
            nativeQuery = true)
    List<Long> findPendingApprovalLines(Long approvalDocCode);

    List<AdditionalApprovalLine> findByApprovalDocCode(Long approvalDocCode);

    @Query(value =
            "SELECT a.approval_document_code " +
                    "FROM tbl_approval_document a " +
                    "INNER JOIN tbl_approval_line b ON a.approval_document_code = b.approval_document_code " +
                    "WHERE a.employee_code = :employeeCode " +
                    "AND b.approval_process_order = (" +
                    "SELECT MAX(approval_process_order) " +
                    "FROM tbl_approval_line " +
                    "WHERE approval_document_code = a.approval_document_code) " +
                    "AND b.approval_process_status = '결재'",
            nativeQuery = true)
    List<Long> finishedInOutboxDocCode(@Param("employeeCode") Long employeeCode); // 매개변수 추가

    @Query(value =
            "SELECT " +
                    "approval_line_code " +
                    "FROM tbl_approval_line " +
                    "WHERE approval_document_code = :approvalDocCode " +
                    "AND approval_process_status = '반려'",
            nativeQuery = true)
    List<Long> findRejectedApprovalLines(Long approvalDocCode);

    @Query(value =
            "SELECT " +
                    "approval_line_code " +
                    "FROM tbl_approval_line " +
                    "WHERE approval_document_code = :approvalDocCode " +
                    "AND approval_process_status = '회수'",
            nativeQuery = true)
    List<Long> findRetrievedApprovalLines(Long approvalDocCode);


    AdditionalApprovalLine findByApprovalDocCodeAndEmployeeCodeAndApprovalProcessStatus(Long approvalDocCode, Long employeeCode, String approvalProcessStatus);

    @Query(value =
            "SELECT " +
                    "a.* " +
                    "FROM tbl_approval_line a " +
                    "WHERE a.employee_code = :employeeCode " +
                    "AND a.approval_process_status != '대기'",
            nativeQuery = true)
    List<AdditionalApprovalLine> findByEmployeeCode(Long employeeCode);

}
