package com.wittypuppy.backend.project.dto;

import com.wittypuppy.backend.project.entity.ProjectMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectAndProjectMemberDTO {
    private Long projectCode;

    private Long projectManagerCode;

    private String projectTitle;

    private String projectDescription;

    private String projectProgressStatus;

    private LocalDateTime projectDeadline;

    private String projectLockedStatus;

    private List<ProjectMemberDTO> projectMemberList;
}
