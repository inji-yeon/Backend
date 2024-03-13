package com.wittypuppy.backend.approval.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "APPROVAL_WORK_TYPE")
@Table(name = "tbl_work_type")
public class WorkType {
    @Id
    @Column(name = "work_type_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workTypeCode;

    @Column(name = "approval_document_code")
    private Long approvalDocCode;

    @Column(name = "work_type_form")
    private String workTypeForm;

    @Column(name = "work_type_title")
    private String workTypeTitle;

    @Column(name = "work_type_start_date")
    private String workTypeStartDate;

    @Column(name = "work_type_end_date")
    private String workTypeEndDate;

    @Column(name = "work_type_place")
    private String workTypePlace;

    @Column(name = "work_type_reason")
    private String workTypeReason;
}
