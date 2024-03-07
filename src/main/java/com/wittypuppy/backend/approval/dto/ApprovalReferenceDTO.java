package com.wittypuppy.backend.approval.dto;

import com.wittypuppy.backend.Employee.dto.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
public class ApprovalReferenceDTO {
    private Long approvalReferenceCode;
    private Long approvalDocCode;
    private Long employeeCode;
    private String whetherCheckedApproval;
}
