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
@Entity(name = "MAINPAGE_PROJECT_PROJECT")
@Table(name = "tbl_project")
public class MainPageProject {
    @Id
    @Column(name = "project_code", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectCode;

    @JoinColumn(name = "project_manager_code")
    @ManyToOne
    private MainPageProjectEmployee projectManager;

    @Column(name = "project_title")
    private String projectTitle;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_progress_status")
    private String projectProgressStatus;

    @Column(name = "project_deadline")
    private LocalDateTime projectDeadline;

    @Column(name = "project_locked_status")
    private String projectLockedStatus;

    @JoinColumn(name = "project_code")
    @OneToMany
    private List<MainPageProjectPost> projectPostList;

    public MainPageProject setProjectCode(Long projectCode) {
        this.projectCode = projectCode;
        return this;
    }

    public MainPageProject setProjectManager(MainPageProjectEmployee projectManager) {
        this.projectManager = projectManager;
        return this;
    }

    public MainPageProject setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
        return this;
    }

    public MainPageProject setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
        return this;
    }

    public MainPageProject setProjectProgressStatus(String projectProgressStatus) {
        this.projectProgressStatus = projectProgressStatus;
        return this;
    }

    public MainPageProject setProjectDeadline(LocalDateTime projectDeadline) {
        this.projectDeadline = projectDeadline;
        return this;
    }

    public MainPageProject setProjectLockedStatus(String projectLockedStatus) {
        this.projectLockedStatus = projectLockedStatus;
        return this;
    }

    public MainPageProject setProjectPostList(List<MainPageProjectPost> projectPostList) {
        this.projectPostList = projectPostList;
        return this;
    }

    public MainPageProject builder() {
        return new MainPageProject(projectCode, projectManager, projectTitle, projectDescription, projectProgressStatus, projectDeadline, projectLockedStatus, projectPostList);
    }
}
