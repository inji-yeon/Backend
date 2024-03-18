package com.wittypuppy.backend.approval.entity;

public class ApprovalDocWithStatus {
    private ApprovalDoc approvalDoc;
    private String status;

    public ApprovalDocWithStatus(ApprovalDoc approvalDoc, String status) {
        this.approvalDoc = approvalDoc;
        this.status = status;
    }

    public ApprovalDoc getApprovalDoc() {
        return approvalDoc;
    }

    public void setApprovalDoc(ApprovalDoc approvalDoc) {
        this.approvalDoc = approvalDoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
