package com.wittypuppy.backend.project.controller;

import com.wittypuppy.backend.common.dto.Criteria;
import com.wittypuppy.backend.common.dto.PageDTO;
import com.wittypuppy.backend.common.dto.PagingResponseDTO;
import com.wittypuppy.backend.common.dto.ResponseDTO;
import com.wittypuppy.backend.project.dto.*;
import com.wittypuppy.backend.project.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/project")
@AllArgsConstructor
@Slf4j
public class ProjectController {
    private final ProjectService projectService;

    /**
     * 프로젝트를 조회한다.
     * <p>
     * 1. 전체 프로젝트 조회<br>
     * 2. 내 프로젝트 조회<br>
     * 3. 내 부서 프로젝트 조회
     * <p>
     * 제목을 통해 검색할 수도 있다. 위의 조회 타입과 합쳐서 사용 가능하다.
     *
     * @param projectType 프로젝트 타입 (빈 값, my-project, my-dept-project)
     * @param searchValue 제목 검색 값
     * @param object      계정 정보
     * @return 200, 메시지, 보낼데이터 반환
     */
    @GetMapping("/projects")
    public ResponseEntity<ResponseDTO> selectProjectListWithPaging(@RequestParam(required = false) String projectType,
                                                                   @RequestParam(required = false) String searchValue,
                                                                   @RequestParam(name = "offset", defaultValue = "1") String offset,
                                                                   @AuthenticationPrincipal Object object) {
        List<ProjectMainDTO> result = null;
        Long userEmployeeCode = 1L;
        Criteria cri = new Criteria(Integer.valueOf(offset), 6);
        if (Objects.isNull(projectType) || projectType.isBlank()) {
            if (Objects.isNull(searchValue) || searchValue.isBlank()) {
                result = projectService.selectProjectListWithPaging(cri);
            } else {
                result = projectService.searchProjectListWithPaging(searchValue, cri);
            }
        } else if (projectType.equals("my-project")) {
            if (Objects.isNull(searchValue) || searchValue.isBlank()) {
                result = projectService.selectMyProjectListWithPaging(userEmployeeCode, cri);
            } else {
                result = projectService.searchMyProjectListWithPaging(userEmployeeCode, searchValue, cri);
            }
        } else if (projectType.equals("my-dept-project")) {
            if (Objects.isNull(searchValue) || searchValue.isBlank()) {
                result = projectService.selectMyDeptProjectListWithPaging(userEmployeeCode, cri);
            } else {
                result = projectService.searchMyDeptProjectListWithPaging(userEmployeeCode, searchValue, cri);
            }
        }
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(result);
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) result.size()));
        return res("프로젝트 검색 성공", pagingResponseDTO);
    }

    /**
     * 프로젝트 정보를 통해 프로젝트를 생성
     * <p>
     * 1. 프로젝트 제목<br>
     * 2. 프로젝트 설명<br>
     * 3. 프로젝트 마감일<br>
     * 4. 프로젝트 잠금 여부<br>
     * <p>
     * 생성한 대상자가 프로젝트 관리자가 된다.
     *
     * @param projectDTO 입력한 프로젝트 정보 값
     * @param object     계정 정보
     * @return 200, 메시지 반환
     */
    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO> createProject(@RequestBody ProjectDTO projectDTO,
                                                     @AuthenticationPrincipal Object object) {
        Long userEmployeeCode = 12L;
        String result = projectService.createProject(projectDTO, userEmployeeCode);

        return res(result);
    }

    /**
     * 프로젝트에 접속한다.
     *
     * @param projectCode 전달받은 프로젝트 식별 코드 값
     * @param object      계정 정보
     * @return 200, 메시지, 보낼데이터 반환
     */
    @GetMapping("/projects/{projectCode}")
    public ResponseEntity<ResponseDTO> openProject(@PathVariable Long projectCode,
                                                   @AuthenticationPrincipal Object object) {
        Long userEmployeeCode = 12L;
        Map<String, Object> result = projectService.openProject(projectCode, userEmployeeCode);
        return res("프로젝트 열기 성공", result);
    }

    @GetMapping("/projects/{projectCode}/paging")
    public ResponseEntity<ResponseDTO> selectProjectPostListWithPaging(
            @PathVariable Long projectCode,
            @RequestParam(name = "offset", defaultValue = "1") String offset) {
        Long userEmployeeCode = 12L;
        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        List<ProjectPostDTO> projectPostList = projectService.selectProjectPostListWithPaging(projectCode, cri, userEmployeeCode);
        pagingResponseDTO.setData(projectPostList);

        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) projectPostList.size()));

        return res("프로젝트 게시글 조회 성공", pagingResponseDTO);
    }

    /**
     * 프로젝트 코드와 프로젝트 정보 값을 전달하면 프로젝트를 수정한다.
     * <p>
     * 1. 프로젝트 제목<br>
     * 2. 프로젝트 설명<br>
     * 3. 프로젝트 진행 상태<br>
     * 4. 프로젝트 마감 기한<br>
     * 5. 프로젝트 잠금 여부
     *
     * @param projectCode       전달받은 프로젝트 식별 코드 값
     * @param projectOptionsDTO 입력한 프로젝트 정보 값
     * @param object            계정 정보
     * @return 200, 메시지 반환
     */
    @PutMapping("/projects/{projectCode}")
    public ResponseEntity<ResponseDTO> modifyProject(@PathVariable Long projectCode,
                                                     @RequestBody ProjectOptionsDTO projectOptionsDTO,
                                                     @AuthenticationPrincipal Object object) {
        Long userEmployeeCode = 12L;
        String result = projectService.modifyProject(projectCode, projectOptionsDTO, userEmployeeCode);
        return res(result);
    }

    /**
     * 프로젝트 멤버로 초대하기 전에 사원 목록 출력을 위해 사원들의 정보를 가져온다.
     *
     * @param object 계정 정보
     * @return 200, 메시지, 사원 리스트 반환
     */
    @GetMapping("/employees")
    public ResponseEntity<ResponseDTO> selectEmployeeList(@AuthenticationPrincipal Object object
    ) {
        Long userEmployeeCode = 12L;
        List<EmployeeDTO> result = projectService.selectEmployeeList();
        return res("프로젝트에 사원 초대를 위한 사원 목록 조회", result);
    }

    /**
     * 사원을 프로젝트에 초대합니다. 관리자만 초대 가능합니다.
     * 이미 프로젝트에 존재하는 사원인 경우 자동으로 제외하고 초대한다.
     *
     * @param projectCode            해당 프로젝트 코드
     * @param inviteEmployeeCodeList 초대할 사원 코드
     * @param object                 계정 정보
     * @return 200, 메시지 반환
     */
    @PostMapping("/projects/{projectCode}/invite")
    public ResponseEntity<ResponseDTO> inviteProjectMembers(@PathVariable Long projectCode,
                                                            @RequestBody List<Long> inviteEmployeeCodeList,
                                                            @AuthenticationPrincipal Object object) {
        Long userEmployeeCode = 12L;
        return res(projectService.inviteProjectMembers(projectCode, inviteEmployeeCodeList, userEmployeeCode));
    }

    /**
     * 프로젝트에서 나간다. 관리자인 경우 다른 사람에게 관리자를 위임해야 한다.
     *
     * @param projectCode 해당 프로젝트 식별 코드
     * @param object      계정 정보
     * @return 200, 메시지 반환
     */
    @DeleteMapping("/projects/{projectCode}/exit")
    public ResponseEntity<ResponseDTO> kickProjectMember(@PathVariable Long projectCode,
                                                         @AuthenticationPrincipal Object object) {
        Long userEmployeeCode = 12L;
        return res(projectService.exitProjectMember(projectCode, userEmployeeCode));
    }

    /**
     * 사원을 강제로 내보낸다. 프로젝트 관리자만 처리 가능하다.
     *
     * @param projectCode             해당 프로젝트 식별 코드
     * @param kickedProjectMemberCode 강제로 내보낼 사원의 프로젝트 멤버 코드
     * @param object                  계정 정보
     * @return 200, 메시지 반환
     */
    @DeleteMapping("/projects/{projectCode}/kick/{kickedProjectMemberCode}")
    public ResponseEntity<ResponseDTO> kickProjectMember(@PathVariable Long projectCode,
                                                         @PathVariable Long kickedProjectMemberCode,
                                                         @AuthenticationPrincipal Object object) {
        Long userEmployeeCode = 12L;
        return res(projectService.kickProjectMember(projectCode, kickedProjectMemberCode, userEmployeeCode));
    }

    /**
     * 정상적인 조회에 성공했을 경우 응답하는 메서드
     *
     * @param msg  메시지
     * @param data 보낼 데이터
     * @return 200, 메시지, 보낼데이터 로 응답
     */
    private ResponseEntity<ResponseDTO> res(String msg, Object data) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, msg, data));
    }

    /**
     * 정상적인 생성, 수정, 삭제에 성공할 경우 응답하는 메서드<br>
     * 반환할 데이터가 없고 이미 메시지에 어느정도 설명이 있으므로 이 Object를 생략한 값을 반환한다.
     *
     * @param msg 메시지
     * @return 200, 메시지 로 응답
     */
    private ResponseEntity<ResponseDTO> res(String msg) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, msg));
    }
}