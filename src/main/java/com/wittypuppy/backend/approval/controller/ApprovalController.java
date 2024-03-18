package com.wittypuppy.backend.approval.controller;

import com.wittypuppy.backend.Employee.dto.User;
import com.wittypuppy.backend.approval.dto.*;
import com.wittypuppy.backend.approval.entity.ApprovalDoc;
import com.wittypuppy.backend.approval.entity.ApprovalDocWithStatus;
import com.wittypuppy.backend.approval.service.ApprovalService;
import com.wittypuppy.backend.common.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


@Tag(name = "전자 결재")
@RestController
@RequestMapping("/approval")
@Slf4j
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // 결재 진행함 - 연장근로
    @GetMapping("overwork-details-op/{approvalDocCode}")
    public ResponseEntity<ResponseDTO> selectOverworkOP(@PathVariable Long approvalDocCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.overworkDetailsOP(approvalDocCode)));
    }

    // 결재 완료함 - 연장근로
    @GetMapping("overwork-details-fin/{approvalDocCode}")
    public ResponseEntity<ResponseDTO> selectOverworkFin(@PathVariable Long approvalDocCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.overworkDetailsFin(approvalDocCode)));
    }

    // 결재 대기함 - 연장근로
    @GetMapping("overwork-details-inbox/{approvalDocCode}")
    public ResponseEntity<ResponseDTO> selectOverworkInbox(@PathVariable Long approvalDocCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.overworkDetailsInbox(approvalDocCode)));
    }
    @GetMapping("overwork-details-inbox-fin/{approvalDocCode}")
    public ResponseEntity<ResponseDTO> selectOverworkInboxFinished(@PathVariable Long approvalDocCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.overworkDetailsInbox(approvalDocCode)));
    }

    @Tag(name = "문서 상신", description = "결재 문서 상신하기")
    @PostMapping("/submit-on-leave")
    public ResponseEntity<ResponseDTO> submitOnLeaveApproval(ApprovalDocDTO approvalDocDTO, MultipartFile file, @AuthenticationPrincipal User user){
        ApprovalDoc savedApprovalDoc = approvalService.saveOnLeaveApprovalDoc(approvalDocDTO, user);
        approvalService.saveOnLeaveDoc(savedApprovalDoc);
        approvalService.saveFirstApprovalLine(savedApprovalDoc, user);

        // 추가 결재자 목록
        List<Long> additionalApprovers = Arrays.asList(1L, 2L, 32L);
        approvalService.saveApprovalLines(savedApprovalDoc, additionalApprovers);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상신 성공"));
    }

    // 첨부 파일 다운로드
    @GetMapping("/attachment/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            byte[] fileContent = approvalService.downloadFile(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 사원 정보 조회
    @GetMapping("/approval-employee/{employeeCode}")
    public ResponseEntity<ResponseDTO> getApprovalEmployee(@PathVariable Long employeeCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "결재 유저 조회 성공", approvalService.findUserDetail(employeeCode)));
    }

    // 로그인한 사원 조회
    @GetMapping("/loggedin-employee")
    public ResponseEntity<ResponseDTO> getLoggedinEmployee(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "로그인한 사원 조회 성공", approvalService.approvalUserInfo(user)));
    }

    @PostMapping("/submit-overwork")
    public ResponseEntity<ResponseDTO> submitOverworkApproval(@ModelAttribute OverworkDTO overworkDTO,
                                                              @RequestParam("additionalApprovers") List<Long> additionalApprovers,
                                                              @RequestParam(value = "refViewers", required = false) List<Long> refViewers,
                                                              @RequestParam(value = "file", required = false) MultipartFile[] files,
                                                              @AuthenticationPrincipal User user) throws IOException {

        System.out.println("submit overwork start=======");

        ApprovalDoc savedApprovalDoc = approvalService.saveOverworkApprovalDoc(overworkDTO, user);
        approvalService.saveFirstApprovalLine(savedApprovalDoc, user);

        // 추가 결재자 목록
        approvalService.saveApprovalLines(savedApprovalDoc, additionalApprovers);

        // 열람자 지정
        if (refViewers != null) {
            approvalService.saveRefViewers(savedApprovalDoc, refViewers);
        }

        // 파일 첨부
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                approvalService.saveAttachement(savedApprovalDoc, file);
            }
        }

        System.out.println("files ===== " + files);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상신 성공"));
    }

    @PostMapping("/submit-sw-use")
    public ResponseEntity<ResponseDTO> submitSWUseApproval(ApprovalDocDTO approvalDocDTO, @AuthenticationPrincipal User user){
        ApprovalDoc savedApprovalDoc = approvalService.saveSWUseveApprovalDoc(approvalDocDTO, user);
        approvalService.saveSWDoc(savedApprovalDoc);
        approvalService.saveFirstApprovalLine(savedApprovalDoc, user);

        // 추가 결재자 목록
        List<Long> additionalApprovers = Arrays.asList(1L, 2L, 32L);
        approvalService.saveApprovalLines(savedApprovalDoc, additionalApprovers);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상신 성공"));
    }

    @PostMapping("/submit-work-type")
    public ResponseEntity<ResponseDTO> submitWorkTypeApproval(ApprovalDocDTO approvalDocDTO, @AuthenticationPrincipal User user){
        ApprovalDoc savedApprovalDoc = approvalService.saveWorkTypeApprovalDoc(approvalDocDTO, user);
        approvalService.saveWorkTypeDoc(savedApprovalDoc);
        approvalService.saveFirstApprovalLine(savedApprovalDoc, user);

        // 추가 결재자 목록
        List<Long> additionalApprovers = Arrays.asList(1L, 2L, 32L);
        approvalService.saveApprovalLines(savedApprovalDoc, additionalApprovers);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상신 성공"));
    }

    @Tag(name = "상신함 - 상신 문서 조회", description = "로그인한 사용자가 상신한 문서 목록 조회")
    @GetMapping("/outbox-approval")
    public ResponseEntity<ResponseDTO> outboxApproval(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> approvalDocs = approvalService.findApprovalDocsByEmployeeCode(user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalDocs));
    }

    @Tag(name = "수신함 - 결재 대기 문서 조회", description = "로그인한 사용자가 결재한 문서 중, 결재 상태가 '대기'인 문서 목록 조회")
    @GetMapping("/inbox-approval")
    public ResponseEntity<ResponseDTO> inboxApproval(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> approvalDocs = approvalService.inboxDocListByEmployeeCode(user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalDocs));
    }

    @Tag(name = "결재", description = "로그인한 사용자가 결재자로 지정된 문서 결재하기")
    @PutMapping("/approvement/{approvalDocCode}")
    public ResponseEntity<ResponseDTO> approvement(@PathVariable Long approvalDocCode, @AuthenticationPrincipal User user){
        System.out.println("approvalDocCode = " + approvalDocCode);
        System.out.println("em = " + user.getEmployeeCode());

        approvalService.approvement(approvalDocCode, user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "결재 성공"));    }

    @Tag(name = "반려", description = "로그인한 사용자가 결재자로 지정된 문서 반려하기")
    @PutMapping("/rejection/{approvalDocCode}")
    public ResponseEntity<ResponseDTO> rejection(@PathVariable Long approvalDocCode,
                                                 @AuthenticationPrincipal User user,
                                                 @RequestBody String rejectionReason){
        System.out.println("approvalDocCode = " + approvalDocCode);
        System.out.println("em = " + user.getEmployeeCode());

        approvalService.rejection(approvalDocCode, user, rejectionReason);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "반려 성공"));
    }

    @Tag(name = "상신 문서 회수", description = "로그인한 사용자가 상신한 문서 중, 첫 번째 결재자가 아직 결재하지 않은 문서 회수하기")
    @PutMapping("/retrieval/{approvalDocCode}")
    public ResponseEntity<ApprovalResultDTO> retrieval(@PathVariable Long approvalDocCode, @AuthenticationPrincipal User user){
        ApprovalResultDTO response = approvalService.retrieval(approvalDocCode, user);
        System.out.println("response ========== " + response);
        return ResponseEntity.ok(response);
    }

    @Tag(name = "결재 진행 중인 문서 조회", description = "로그인한 사용자가 결재자로 지정된 문서 중, 결재 프로세스가 완료되지 않은 문서 목록 조회")
    @GetMapping("/outbox-on-process")
    public ResponseEntity<ResponseDTO> onProcessInOutbox(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> approvalDocs = approvalService.onProcessInOutbox(user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalDocs));
    }

    @Tag(name = "수신함 - 결재 완료 문서 조회", description = "로그인한 사용자가 결재자로 지정된 문서 중, 결재 프로세스가 '결재'로 완료된 문서 목록 조회")
    @GetMapping("/outbox-finished")
    public ResponseEntity<ResponseDTO> finishedInOutbox(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> approvalDocs = approvalService.finishedInOutbox(user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalDocs));
    }

    @Tag(name = "수신함 - 반려 문서 조회", description = "로그인한 사용자가 결재자로 지정된 문서 중, 결재 프로세스가 '반려'로 완료된 문서 목록 조회")
    @GetMapping("/outbox-rejected")
    public ResponseEntity<ResponseDTO> rejectedInOutbox(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> approvalDocs = approvalService.rejectedInOutbox(user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalDocs));
    }

    @Tag(name = "상신함 - 회수 문서 조회", description = "로그인한 사용자가 상신한 문서 중, 회수된 문서 목록 조회하기")
    @GetMapping("/outbox-retrieved")
    public ResponseEntity<ResponseDTO> retrievedInOutbox(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> approvalDocs = approvalService.retrievedInOutbox(user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalDocs));
    }

    // 임시 저장 - 연장근로
    @PostMapping("/temp-save-overwork")
    public ResponseEntity<ResponseDTO> temporarySaveOverwork(@ModelAttribute OverworkDTO overworkDTO, @AuthenticationPrincipal User user){
        System.out.println("save overwork start=======");

        ApprovalDoc savedApprovalDoc = approvalService.temporarySaveOverwork(overworkDTO, user);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "저장 성공"));
    }

    @Tag(name = "임시 저장 문서 조회", description = "임시 저장된 문서 목록 조회하기")
    @GetMapping("/outbox-saved")
    public ResponseEntity<ResponseDTO> savedOutbox(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> approvalDocs = approvalService.findSavedDocsByEmployeeCode(user);
        System.out.println("approvalDocs = " + approvalDocs);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalDocs));
    }

    @Tag(name = "상신함 - 결재 완료 문서 조회", description = "로그인한 사용자가 상신한 문서 중, 결재 프로세스가 '결재'로 완료된 문서 목록 조회")
    @GetMapping("/inbox-finished")
    public ResponseEntity<ResponseDTO> inboxFinished(@AuthenticationPrincipal User user) {
        List<ApprovalDoc> finishedDocs = approvalService.inboxFinishedListByEmployeeCode(user);
        System.out.println("finishedDocs = " + finishedDocs);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", finishedDocs));
    }

    // 결재 수신함 - 결재 완료함 조회
    @GetMapping("/inbox-finished-docs")
    public ResponseEntity<ResponseDTO> inboxFinishedDocsWithStatus(@AuthenticationPrincipal User user) {
        List<ApprovalDocWithStatus> finishedDocsWithStatus = approvalService.findFinishedDocsWithStatusInbox(user);
        System.out.println("finishedDocsWithStatus = " + finishedDocsWithStatus);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", finishedDocsWithStatus));
    }
}