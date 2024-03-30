package com.wittypuppy.backend.approval.entity;

import com.wittypuppy.backend.Employee.entity.LoginEmployee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "APPROVAL_ON_LEAVE")
@Table(name = "tbl_on_leave")
public class OnLeave {
    @Id
    @Column(name = "on_leave_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long onLeaveCode;

    @Column(name = "approval_document_code")
    private Long approvalDocCode;

    @Column(name = "on_leave_title")
    private String onLeaveTitle;

    @Column(name = "remaining_on_leave")
    private Long onLeaveCount;

    @Column(name = "kind_of_on_leave")
    private String kindOfOnLeave;

    @Column(name = "on_leave_start_date")
    private String onLeaveStartDate;

    @Column(name = "on_leave_end_date")
    private String onLeaveEndDate;

    @Column(name = "on_leave_reason")
    private String onLeaveReason;
}
