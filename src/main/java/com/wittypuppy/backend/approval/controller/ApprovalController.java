package com.wittypuppy.backend.approval.controller;

import com.wittypuppy.backend.Employee.dto.User;
import com.wittypuppy.backend.approval.dto.ApprovalDocDTO;
import com.wittypuppy.backend.approval.dto.ApprovalRepresentDTO;
import com.wittypuppy.backend.approval.entity.ApprovalDoc;
import com.wittypuppy.backend.approval.service.ApprovalService;
import com.wittypuppy.backend.common.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/{approvalDocCode}")
//    public ResponseEntity<ResponseDTO> selectInboxDoc(@PathVariable Long approvalDocCode){
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.selectInboxDoc(approvalDocCode)));
//    }

    @Tag(name = "문서 상신", description = "결재 문서 상신하기")
    @PostMapping("/submit-on-leave")
    public ResponseEntity<ResponseDTO> submitOnLeaveApproval(ApprovalDocDTO approvalDocDTO, @AuthenticationPrincipal User user){
        ApprovalDoc savedApprovalDoc = approvalService.saveOnLeaveApprovalDoc(approvalDocDTO, user);
        approvalService.saveOnLeaveDoc(savedApprovalDoc);
        approvalService.saveFirstApprovalLine(savedApprovalDoc, user);

        // 추가 결재자 목록
        List<Long> additionalApprovers = Arrays.asList(1L, 2L, 32L);
        approvalService.saveApprovalLines(savedApprovalDoc, additionalApprovers);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상신 성공"));
    }

    @PostMapping("/submit-overwork")
    public ResponseEntity<ResponseDTO> submitOverworkApproval(ApprovalDocDTO approvalDocDTO, @AuthenticationPrincipal User user){
        ApprovalDoc savedApprovalDoc = approvalService.saveOverworkApprovalDoc(approvalDocDTO, user);
        approvalService.saveOverworkDoc(savedApprovalDoc);
        approvalService.saveFirstApprovalLine(savedApprovalDoc, user);

        // 추가 결재자 목록
        List<Long> additionalApprovers = Arrays.asList(1L, 2L, 32L);
        approvalService.saveApprovalLines(savedApprovalDoc, additionalApprovers);

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
    public ResponseEntity<String> approvement(@PathVariable Long approvalDocCode, @AuthenticationPrincipal User user){
        System.out.println("approvalDocCode = " + approvalDocCode);
        System.out.println("em = " + user.getEmployeeCode());

        String result = approvalService.approvement(approvalDocCode, user);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    @Tag(name = "대리 결재 지정", description = "대리 결재자 지정하기")
    @PostMapping("/set-represent")
    public ResponseEntity<ResponseDTO> setRepresent(ApprovalRepresentDTO approvalRepresentDTO, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "지정 성공", approvalService.setRepresent(approvalRepresentDTO, user)));
    }

    @Tag(name = "반려", description = "로그인한 사용자가 결재자로 지정된 문서 반려하기")
    @PutMapping("/rejection/{approvalDocCode}")
    public ResponseEntity<String> rejection(@PathVariable Long approvalDocCode, @AuthenticationPrincipal User user){
        System.out.println("approvalDocCode = " + approvalDocCode);
        System.out.println("em = " + user.getEmployeeCode());

        String result = approvalService.rejection(approvalDocCode, user);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
    }

    @Tag(name = "상신 문서 회수", description = "로그인한 사용자가 상신한 문서 중, 첫 번째 결재자가 아직 결재하지 않은 문서 회수하기")
    @PutMapping("/retrieval/{approvalDocCode}")
    public ResponseEntity<String> retrieval(@PathVariable Long approvalDocCode, @AuthenticationPrincipal User user){
        String result = approvalService.retrieval(approvalDocCode, user);
        System.out.println("result ========== " + result);
        return ResponseEntity.ok(result);
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

    @Tag(name = "임시 저장", description = "결재 문서 임시 저장하기")
    @PostMapping("/save-approval")
    public ResponseEntity<ResponseDTO> saveApproval(ApprovalDocDTO approvalDocDTO, @AuthenticationPrincipal User user){
        approvalService.temporarySaveApprovalDoc(approvalDocDTO, user);
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

    // 열람자 지정하기

    // 열람함 문서 조회

    // 대리결재자 지정하기

    // 수신함 - 대리결재

    // 결재 문서 내용 추가

    // 휴가 일수 차감

    // 결재선 삭제

    // 결재선 순서 변경

    // 재기안
}