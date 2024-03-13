package com.wittypuppy.backend.approval.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalEmployeeDTO {
    private Long employeeCode;
    private Long departmentCode;
    private Long jobCode;
    private String employeeId;
    private String employeeName;
    private Long employeeOnLeaveCount;
    private ApprovalDepartmentDTO department; // 부서 정보를 포함하는 DTO
}
