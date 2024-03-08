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
    private Long approvalDocCode;
    private String approvalOgFile;
    private String approvalChangedFile;
    private String whetherDeletedApprovalAttached;
    private String apFilePathOrigin;
    private String apFilePathChange;
}
