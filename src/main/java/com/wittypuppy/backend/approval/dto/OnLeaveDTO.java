package com.wittypuppy.backend.approval.dto;

import com.wittypuppy.backend.Employee.dto.User;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OnLeaveDTO {
    private Long onLeaveCode;
    private Long approvalDocCode;
    private String onLeaveTitle;
    private User onLeaveCount;
    private String kindOfOnLeave;
    private String onLeaveStartDate;
    private String onLeaveEndDate;
    private String onLeaveReason;
    private String approvalTitle;
}
