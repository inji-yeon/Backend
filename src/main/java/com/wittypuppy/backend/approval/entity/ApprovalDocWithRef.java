package com.wittypuppy.backend.approval.entity;

public class ApprovalDocWithRef {
    private ApprovalDoc approvalDoc;
    private String whetherChecked;

    public ApprovalDocWithRef(ApprovalDoc approvalDoc, String whetherChecked) {
        this.approvalDoc = approvalDoc;
        this.whetherChecked = whetherChecked;
    }

    public ApprovalDoc getApprovalDoc() {
        return approvalDoc;
    }

    public void setApprovalDoc(ApprovalDoc approvalDoc) {
        this.approvalDoc = approvalDoc;
    }

    public String getWhetherChecked() {
        return whetherChecked;
    }

    public void setWhetherChecked(String whetherChecked) {
        this.whetherChecked = whetherChecked;
    }
}
