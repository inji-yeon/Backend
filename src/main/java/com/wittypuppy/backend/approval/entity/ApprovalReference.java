package com.wittypuppy.backend.approval.entity;

import com.wittypuppy.backend.Employee.entity.LoginEmployee;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "APPROVAL_REFERENCE")
@Table(name = "tbl_approval_reference")
public class ApprovalReference {
    @Id
    @Column(name = "approval_reference_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalReferenceCode;

    @Column(name = "approval_document_code")
    private Long approvalDocCode;

    @Column(name = "employee_code")
    private Long employeeCode;

    @Column(name = "whether_checked_approval")
    private String whetherCheckedApproval;
}
