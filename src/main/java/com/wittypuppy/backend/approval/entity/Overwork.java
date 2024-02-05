package com.wittypuppy.backend.approval.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "APPROVAL_OVERWORK")
@Table(name = "tbl_overwork")
public class Overwork {
    @Id
    @Column(name = "ovework_code")
    private Long overworkCode;

    @JoinColumn(name = "approval_document_code")
    @ManyToOne
    private ApprovalDoc approvalDoc;

    @Column(name = "overwork_title")
    private String overworkTitle;

    @Column(name = "kind_of_overwork")
    private String kindOfOverwork;

    @Column(name = "overwork_date")
    private LocalDateTime overworkDate;

    @Column(name = "overwork_start_time")
    private LocalDateTime overworkStartTime;

    @Column(name = "overwork_end_time")
    private LocalDateTime overworkEndTime;

    @Column(name = "overwork_reason")
    private String overworkReason;
}
