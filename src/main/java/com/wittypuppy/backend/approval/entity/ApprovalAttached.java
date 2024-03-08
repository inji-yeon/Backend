package com.wittypuppy.backend.approval.entity;

import com.wittypuppy.backend.calendar.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "apfilepath_origin")
    private String apFilePathOrigin;

    @Column(name = "apfilepath_change")
    private String apFilePathChange;

    public ApprovalAttached() {
    }

    public ApprovalAttached approvalAttachedCode(Long approvalAttachedCode){
        this.approvalAttachedCode = approvalAttachedCode;
        return this;
    }
    public ApprovalAttached approvalDocCode(Long approvalDocCode) {
        this.approvalDocCode = approvalDocCode;
        return this;
    }

    public ApprovalAttached approvalOgFile(String approvalOgFile) {
        this.approvalOgFile = approvalOgFile;
        return this;
    }

    public ApprovalAttached approvalChangedFile(String approvalChangedFile) {
        this.approvalChangedFile = approvalChangedFile;
        return this;
    }

    public ApprovalAttached whetherDeletedApprovalAttached(String whetherDeletedApprovalAttached) {
        this.whetherDeletedApprovalAttached = whetherDeletedApprovalAttached;
        return this;
    }

    public ApprovalAttached apFilePathOrigin(String apFilePathOrigin) {
        this.apFilePathOrigin = apFilePathOrigin;
        return this;
    }
    public ApprovalAttached apFilePathChange(String apFilePathChange) {
        this.apFilePathChange = apFilePathChange;
        return this;
    }

    public ApprovalAttached build() {
        return new ApprovalAttached(approvalAttachedCode, approvalDocCode, approvalOgFile, approvalChangedFile, whetherDeletedApprovalAttached, apFilePathOrigin, apFilePathChange);
    }

}
