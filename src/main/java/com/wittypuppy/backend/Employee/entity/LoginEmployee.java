package com.wittypuppy.backend.Employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = "LOGIN_EMPLOYEE")
@Table(name = "tbl_employee")
//@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginEmployee {

    @Id
    @Column(name = "employee_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeCode;

    @Column(name = "employee_id", length = 100, unique = true, nullable = false)
    private String employeeId;

    @Column(name = "employee_password", length = 500, nullable = false)
    private String employeePassword;

    @Column(name = "employee_name", length = 100, nullable = false)
    private String employeeName;

    @Column(name = "employee_external_email", length = 100)
    private String employeeEmail;

    @OneToMany
    @JoinColumn(name = "employee_code")
    private List<LoginEmployeeRole> employeeRole;

    @Column(name="employee_retirement_date")
    private String employeeRetireDate;

    @Column(name = "employee_birth_date")
    private Date employeeBirthDate;

    @Column(name = "employee_join_date")
    private Date joinDate;

    @Column(name = "employee_address")
    private String empAddress;

    @Column(name = "employee_phone")
    private String empPhone;

    @JoinColumn(name = "department_code")
    @ManyToOne
    private LoginDepartment empDepartment;

    @Column(name = "employee_on_leave_count")
    private Long employeeOnLeaveCount;

    protected LoginEmployee(int employeeCode, String employeeId, String employeePassword, String employeeName, String employeeEmail, List<LoginEmployeeRole> employeeRole, String employeeRetireDate, Date employeeBirthDate, Long employeeOnLeaveCount) {}

    public LoginEmployee() {

    }

    public void setEmpDepartment(LoginDepartment empDepartment) {
        this.empDepartment = empDepartment;
    }

    public LoginEmployee employeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
        return this;
    }

    public LoginEmployee employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public LoginEmployee employeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
        return this;
    }

    public LoginEmployee employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public LoginEmployee employeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
        return this;
    }

    public LoginEmployee employeeRole(List<LoginEmployeeRole> employeeRole) {
        this.employeeRole = employeeRole;
        return this;
    }

    public LoginEmployee employeeBirthDate(Date employeeBirthDate) {
        this.employeeBirthDate = employeeBirthDate;
        return this;
    }

    public LoginEmployee employeeOnLeaveCount(Long employeeOnLeaveCount) {
        this.employeeOnLeaveCount = employeeOnLeaveCount;
        return this;
    }

    //    public LoginEmployee build() {
//        return new Member(memberCode, memberId, memberPassword, memberName, memberEmail, memberRole, memberStatus);
//    }

    //builder생성
    public LoginEmployee build(){
        return new LoginEmployee(employeeCode, employeeId, employeePassword, employeeName, employeeEmail, employeeRole, employeeRetireDate, employeeBirthDate, joinDate, empAddress, empPhone, empDepartment, employeeOnLeaveCount );
    }



}
