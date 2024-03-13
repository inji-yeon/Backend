package com.wittypuppy.backend.approval.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "APPROVAL_DEPARTMENT")
@Table(name = "tbl_department")
public class ApprovalDepartment {
    @Id
    @Column(name = "department_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentCode;

    @Column(name = "parent_department_code")
    private Long parentDepartmentCode;

    @Column(name = "department_name")
    private String departmentName;
}
