package com.wittypuppy.backend.approval.entity;

import com.wittypuppy.backend.calendar.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity(name = "APPROVAL_ATTACHED")
@Table(name = "tbl_approval_attached")
public class ApprovalAttached {
    @Id
    @Column(name = "approval_attached_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalAttachedCode;

    @Column(name = "approval_document_code")
    private Long approvalDocCode;

    @Column(name = "approval_og_file")
    private String approvalOgFile;

    @Column(name = "approval_changed_file")
    private String approvalChangedFile;


    @Column(name = "whether_deleted_approval_attached")
    private String whetherDeletedApprovalAttached;

    public ApprovalAttached setApprovalDocCode(Long approvalDocCode){
        this.approvalDocCode = approvalDocCode;
        return this;
    }

    public ApprovalAttached setApprovalOgFile(String approvalChangedFile){
        this.approvalOgFile = approvalOgFile;
        return this;
    }

    public ApprovalAttached setApprovalChangedFile(String approvalChangedFile){
        this.approvalChangedFile = approvalChangedFile;
        return this;
    }

    public ApprovalAttached setWhetherDeletedApprovalAttached(String whetherDeletedApprovalAttached){
        this.whetherDeletedApprovalAttached = whetherDeletedApprovalAttached;
        return this;
    }

    public ApprovalAttached builder() {
        return new ApprovalAttached(approvalAttachedCode, approvalDocCode, approvalOgFile, approvalChangedFile, whetherDeletedApprovalAttached);
    }
}
