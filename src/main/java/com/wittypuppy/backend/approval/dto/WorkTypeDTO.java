package com.wittypuppy.backend.approval.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WorkTypeDTO {
    private Long workTypeCode;
    private Long approvalDocCode;
    private String workTypeForm;
    private String workTypeTitle;
    private String workTypeStartDate;
    private String workTypeEndDate;
    private String workTypePlace;
    private String workTypeReason;
    private String approvalTitle;
}
