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
@Entity(name = "APPROVAL_SOFTWARE_USE")
@Table(name = "tbl_software_use")
public class SoftwareUse {
    @Id
    @Column(name = "software_use_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long softwareUseCode;

    @Column(name = "approval_document_code")
    private Long approvalDocCode;

    @Column(name = "software_title")
    private String softwareTitle;

    @Column(name = "kind_of_software")
    private String kindOfSoftware;

    @Column(name = "software_reason")
    private String softwareReason;

    @Column(name = "software_start_date")
    private String softwareStartDate;
}
