package com.wittypuppy.backend.mainpage.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity(name = "MAINPAGE_PROJECT_PROJECT_POST")
@Table(name = "tbl_project_post")
public class MainPageProjectPost {
    @Id
    @Column(name = "project_post_code", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectPostCode;

    @Column(name = "project_code")
    private Long projectCode;

    @Column(name = "project_post_status")
    private String projectPostStatus;

    @Column(name = "project_post_priority")
    private String projectPostPriority;

    @Column(name = "project_post_title")
    private String projectPostTitle;

    @Column(name = "project_post_creation_date")
    private LocalDateTime projectPostCreationDate;

    @Column(name = "project_post_modify_date")
    private LocalDateTime projectPostModifyDate;

    @Column(name = "project_post_due_date")
    private LocalDateTime projectPostDueDate;

    public MainPageProjectPost setProjectPostCode(Long projectPostCode) {
        this.projectPostCode = projectPostCode;
        return this;
    }

    public MainPageProjectPost setProjectCode(Long projectCode) {
        this.projectCode = projectCode;
        return this;
    }

    public MainPageProjectPost setProjectPostStatus(String projectPostStatus) {
        this.projectPostStatus = projectPostStatus;
        return this;
    }

    public MainPageProjectPost setProjectPostPriority(String projectPostPriority) {
        this.projectPostPriority = projectPostPriority;
        return this;
    }

    public MainPageProjectPost setProjectPostTitle(String projectPostTitle) {
        this.projectPostTitle = projectPostTitle;
        return this;
    }

    public MainPageProjectPost setProjectPostCreationDate(LocalDateTime projectPostCreationDate) {
        this.projectPostCreationDate = projectPostCreationDate;
        return this;
    }

    public MainPageProjectPost setProjectPostModifyDate(LocalDateTime projectPostModifyDate) {
        this.projectPostModifyDate = projectPostModifyDate;
        return this;
    }

    public MainPageProjectPost setProjectPostDueDate(LocalDateTime projectPostDueDate) {
        this.projectPostDueDate = projectPostDueDate;
        return this;
    }

    public MainPageProjectPost builder() {
        return new MainPageProjectPost(projectPostCode, projectCode, projectPostStatus, projectPostPriority, projectPostTitle, projectPostCreationDate, projectPostModifyDate, projectPostDueDate);
    }
}
