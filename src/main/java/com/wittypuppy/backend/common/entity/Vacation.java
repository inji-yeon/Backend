package com.wittypuppy.backend.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_vacation")
public class Vacation {
    @Id
    @Column(name = "vacation_code", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacationCode;

    @Column(name = "employee_code", columnDefinition = "BIGINT")
    private Long employeeCode;

    @Column(name = "vacation_creation_date", columnDefinition = "DATETIME")
    private LocalDateTime vacationCreationDate;

    @Column(name = "vacation_expiration_date", columnDefinition = "DATETIME")
    private LocalDateTime vacationExpirationDate;

    @Column(name = "vacation_creation_reason", columnDefinition = "VARCHAR(500)")
    private String vacationCreationReason;

    @Column(name = "vacation_usage_date", columnDefinition = "DATETIME")
    private LocalDateTime vacationUsageDate;

    @Column(name = "vacation_used_status", columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String vacationUsedStatus;
}
