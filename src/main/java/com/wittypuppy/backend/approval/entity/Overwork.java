package com.wittypuppy.backend.approval.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long overworkCode;

    @Column(name = "approval_document_code")
    private Long approvalDocCode;

    @Column(name = "overwork_title")
    private String overworkTitle;

    @Column(name = "kind_of_overwork")
    private String kindOfOverwork;

    @Column(name = "overwork_date")
    private String overworkDate;

    @Column(name = "overwork_start_time")
    private String overworkStartTime;

    @Column(name = "overwork_end_time")
    private String overworkEndTime;

    @Column(name = "overwork_reason")
    private String overworkReason;
}
