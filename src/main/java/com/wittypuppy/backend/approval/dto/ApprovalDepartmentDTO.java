package com.wittypuppy.backend.approval.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalDepartmentDTO {
    private Long departmentCode;
    private Long parentDepartmentCode;
    private String departmentName;
}
