package com.wittypuppy.backend.approval.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalAttachedDTO {
    private Long approvalAttachedCode;
    private ApprovalDocDTO approvalDocDTO;
    private String approvalOgFile;
    private String approvalChangedFile;
    private String whetherDeletedApprovalAttached;
}
