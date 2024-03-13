package com.wittypuppy.backend.approval.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "APPROVAL_EMPLOYEE")
@Table(name = "tbl_employee")
public class ApprovalEmployee {
    @Id
    @Column(name = "employee_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeCode;

    @Column(name = "department_code")
    private Long departmentCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_code", referencedColumnName = "department_code", insertable = false, updatable = false)
    private ApprovalDepartment department;

    @Column(name = "job_code")
    private Long jobCode;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_on_leave_count")
    private Long employeeOnLeaveCount;
}
