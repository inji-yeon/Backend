package com.wittypuppy.backend.project.controller;

import com.wittypuppy.backend.common.dto.ResponseDTO;
import com.wittypuppy.backend.project.dto.ProjectDTO;
import com.wittypuppy.backend.project.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@AllArgsConstructor
@Slf4j
public class ProjectController {
    private final ProjectService projectService;

    /*전체 프로젝트 리스트*/
    /*내 프로젝트 리스트*/
    /*내 부서 프로젝트 리스트*/
    @GetMapping("/projects")
    public ResponseEntity<ResponseDTO> selectProjectListByType(
            @RequestParam(required = false) String type
    ) {
        log.info("[CalendarController] >>> selectEventByEventCode >>> start");
        Long employeeCode = 1L;

        List<ProjectDTO> projectListDTO = projectService.selectProjectListByTypeAndSearchValue(type, "", employeeCode);

        log.info("[CalendarController] >>> selectEventByEventCode >>> end");
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "프로젝트 리스트 조회 성공", projectListDTO));
    }

    @GetMapping("/projects/search")
    public ResponseEntity<ResponseDTO> selectProjectListByTypeAndSearchValue(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String searchValue
    ) {
        log.info("[CalendarController] >>> selectProjectListByTypeAndSearchValue >>> start");
        Long employeeCode = 1L;

        List<ProjectDTO> projectListDTO = projectService.selectProjectListByTypeAndSearchValue(type, searchValue, employeeCode);

        log.info("[CalendarController] >>> selectProjectListByTypeAndSearchValue >>> end");
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "프로젝트 리스트 검색 성공", projectListDTO));
    }

    @PostMapping("/projects/create")
    public ResponseEntity<ResponseDTO> createProject(
            @RequestBody ProjectDTO projectDTO
    ) {
        log.info("[CalendarController] >>> createProject >>> start");
        Long employeeCode = 1L;

        String resultStr = projectService.createProject(projectDTO, employeeCode);

        log.info("[CalendarController] >>> createProject >>> end");
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "프로젝트 리스트 검색 성공", resultStr));
    }
}
