package com.wittypuppy.backend.attendance.repository;


import com.wittypuppy.backend.attendance.entity.ApprovalLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface AttendanceApprovalRepository extends JpaRepository<ApprovalLine, Long> {


    //내 신청 문서 반려
    @Query(value = "SELECT " +
            "A.approval_document_code, " +
            "A.approval_line_code, " +
            "A.approval_process_date, " +
            "A.approval_process_order, " +
            "A.employee_code, " +
            "A.approval_process_status, " +
            "A.approval_rejected_reason " +
            "FROM tbl_approval_line A " +
            "LEFT JOIN tbl_approval_document B ON (A.employee_code = B.employee_code) " +
            "WHERE A.approval_process_status = '반려' " +
            "AND A.approval_document_code " +
            "IN (SELECT approval_document_code " +
            "FROM tbl_approval_document  " +
            "WHERE employee_code = :empCode) ",
            nativeQuery = true)
    Page<ApprovalLine> findByApprovalProcessStatusAndLineEmployeeCode_EmployeeCodeNative(Pageable pageable, Long empCode);


//내 문서 승인
    @Query(value = "SELECT " +
            "A.approval_document_code, " +
            "A.approval_line_code, " +
            "A.approval_process_date, " +
            "A.approval_process_order, " +
            "A.employee_code, " +
            "A.approval_process_status, " +
            "A.approval_rejected_reason " +
            "FROM tbl_approval_line A " +
            "LEFT JOIN tbl_approval_document B ON A.employee_code = B.employee_code " +
            "WHERE A.approval_process_status = '결재' " +
            "AND A.approval_document_code IN (SELECT approval_document_code " +
            "FROM tbl_approval_document " +
            "WHERE employee_code = :employeeCode) " +
            "AND A.approval_process_order = (SELECT MAX(approval_process_order) " +
            "FROM tbl_approval_line " +
            "WHERE A.approval_document_code = approval_document_code) ",
            nativeQuery = true)
    Page<ApprovalLine> findMyDocumentPayment(Long employeeCode, Pageable paging);


    //내 기안 문서
    @Query(value = "SELECT " +
            "A.approval_document_code, " +
            "A.approval_line_code, " +
            "A.approval_process_date, " +
            "A.approval_process_order, " +
            "A.employee_code, " +
            "A.approval_process_status, " +
            "A.approval_rejected_reason " +
            "FROM tbl_approval_line A " +
            "WHERE A.approval_process_status = '기안' " +
            "AND A.approval_process_order = 1 " +
            "AND A.employee_code = :employeeCode",
            nativeQuery = true)
    Page<ApprovalLine> findByApplyDocument(Long employeeCode, Pageable paging);


    // 내가 결재한 문서
    Page<ApprovalLine> findByLineEmployeeCode_employeeCodeAndApprovalProcessStatus(Pageable paging, Long employeeCode, String 결재);



    //내가 결재할 문서 - 대기

    @Query(value = "SELECT " +
            "A.approval_document_code, " +
            "A.approval_line_code, " +
            "A.approval_process_date, " +
            "A.approval_process_order, " +
            "A.employee_code, " +
            "A.approval_process_status, " +
            "A.approval_rejected_reason " +
            "FROM tbl_approval_line A " +
            "LEFT JOIN tbl_approval_document B ON A.employee_code = B.employee_code " +
            "WHERE A.approval_process_status = '대기' " +
            "AND A.employee_code = :employeeCode " +
            "AND A.approval_process_order IN (SELECT approval_process_order - 1 FROM tbl_approval_line) " +
            "AND ((A.approval_process_status IN ('결재'or '기안') " +
            "      AND A.approval_process_order - 1 IN (SELECT approval_process_order FROM tbl_approval_line " +
            "                                           WHERE approval_process_status IN ('결재'or '기안'))) " +
            "    OR " +
            "    (A.approval_process_status IN ('대기'or '반려'or '회수') " +
            "      AND A.approval_process_order - 1 IN (SELECT approval_process_order FROM tbl_approval_line " +
            "                                           WHERE approval_process_status IN ('대기'or '반려'or '회수'))))",
            nativeQuery = true)
    Page<ApprovalLine> paymentWaiting(Pageable paging, Long employeeCode);
}

