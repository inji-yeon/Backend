package com.wittypuppy.backend.approval.dto;

import com.wittypuppy.backend.Employee.dto.User;
import com.wittypuppy.backend.approval.entity.OnLeave;
import com.wittypuppy.backend.approval.entity.Overwork;
import com.wittypuppy.backend.approval.entity.SoftwareUse;
import com.wittypuppy.backend.approval.entity.WorkType;
import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
public class ApprovalDocDTO {

    private Long approvalDocCode;
    private String approvalForm;
    private User employeeCode;
    private LocalDateTime approvalRequestDate;
    private String whetherSavingApproval;
    private String lastLineStatus;
}
