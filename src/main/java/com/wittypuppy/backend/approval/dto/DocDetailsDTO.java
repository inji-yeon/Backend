package com.wittypuppy.backend.approval.dto;

import com.wittypuppy.backend.approval.entity.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
public class DocDetailsDTO {
    private ApprovalDoc approvalDoc;

    private Overwork overwork;
    private OnLeave onLeave;
    private SoftwareUse softwareUse;
    private WorkType workType;

    private List<ApprovalEmployeeDTO> employeeDTOs;
    private List<ApprovalEmployeeDTO> referenceEmployeeDTOs;
    private List<ApprovalDepartmentDTO> departmentDTOs;

    private List<AdditionalApprovalLine> additionalApprovalLines;
    private List<ApprovalReference> approvalReferences;
    private List<ApprovalAttached> approvalAttachedFiles;

    private boolean availability;
}
