package com.wittypuppy.backend.approval.service;

import com.wittypuppy.backend.Employee.dto.User;
import com.wittypuppy.backend.Employee.entity.LoginEmployee;
import com.wittypuppy.backend.approval.dto.ApprovalDocDTO;
import com.wittypuppy.backend.approval.dto.ApprovalEmployeeDTO;
import com.wittypuppy.backend.approval.dto.ApprovalRepresentDTO;
import com.wittypuppy.backend.approval.dto.OverworkDTO;
import com.wittypuppy.backend.approval.entity.*;
import com.wittypuppy.backend.approval.repository.*;
import com.wittypuppy.backend.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ast.tree.expression.Over;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
public class ApprovalService {
    private final ModelMapper modelMapper;
    private final ApprovalDocRepository approvalDocRepository;
    private final ApprovalLineRepository approvalLineRepository;
    private final AdditionalApprovalLineRepository additionalApprovalLineRepository;
    private final OnLeaveRepository onLeaveRepository;
    private final OverworkRepository overworkRepository;
    private final SoftwareUseRepository softwareUseRepository;
    private final WorkTypeRepository workTypeRepository;
    private final ApprovalRepresentRepository approvalRepresentRepository;
    private final ApprovalAttachedRepository approvalAttachedRepository;
    private final ApprovalEmployeeRepository approvalEmployeeRepository;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;


    public ApprovalService(ModelMapper modelMapper, ApprovalDocRepository approvalDocRepository, ApprovalLineRepository approvalLineRepository, AdditionalApprovalLineRepository additionalApprovalLineRepository, OnLeaveRepository onLeaveRepository, OverworkRepository overworkRepository, SoftwareUseRepository softwareUseRepository, WorkTypeRepository workTypeRepository, ApprovalRepresentRepository approvalRepresentRepository, ApprovalAttachedRepository approvalAttachedRepository, ApprovalEmployeeRepository approvalEmployeeRepository) {
        this.modelMapper = modelMapper;
        this.approvalDocRepository = approvalDocRepository;
        this.approvalLineRepository = approvalLineRepository;
        this.additionalApprovalLineRepository = additionalApprovalLineRepository;
        this.onLeaveRepository = onLeaveRepository;
        this.overworkRepository = overworkRepository;
        this.softwareUseRepository = softwareUseRepository;
        this.workTypeRepository = workTypeRepository;
        this.approvalRepresentRepository = approvalRepresentRepository;
        this.approvalAttachedRepository = approvalAttachedRepository;
        this.approvalEmployeeRepository = approvalEmployeeRepository;
    }

//    public ApprovalDocDTO selectInboxDoc(Long approvalDocCode) {
//        log.info("[ApprovalService] selectInboxDoc start=====");
//
//        ApprovalDoc approvalDoc = approvalDocRepository.findById(approvalDocCode).get();
//        ApprovalDocDTO approvalDocDTO = modelMapper.map(approvalDoc, ApprovalDocDTO.class);
//
//        log.info("[ApprovalService] selectInboxDoc End=====");
//
//        return approvalDocDTO;
//    }

//    @Transactional
//    public String submitApproval(ApprovalDocDTO approvalDocDTO, EmployeeDTO employeeDTO) {
//        log.info("[ApprovalService] submitApproval start=====");
//
//        int result = 0;
//
//        // 문서 정보 저장
//        ApprovalDoc newDoc = modelMapper.map(approvalDocDTO, ApprovalDoc.class);
//        newDoc.setApprovalRequestDate(LocalDateTime.now());
//
//        // 로그인한 사용자의 employeeCode를 가져와서 설정
//        LoginEmployee loginEmployee = modelMapper.map(employeeDTO, LoginEmployee.class);
//        newDoc.setEmployeeCode(loginEmployee);
//
//        approvalDocRepository.save(newDoc);
//
//        result = 1;
//
//    return result > 0 ? "상신 성공" : "상신 실패";
//    }


//    // 기안 문서 정보 저장
//    public ApprovalDoc saveApprovalDoc(ApprovalDocDTO approvalDocDTO, User user) {
//        log.info("[ApprovalService] saving doc info started =====");
//
//        ApprovalDoc approvalDoc = modelMapper.map(approvalDocDTO, ApprovalDoc.class);
//        approvalDoc.setApprovalForm("휴가신청서");
//
//        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
//        approvalDoc.setEmployeeCode(loginEmployee);
//
//        approvalDoc.setApprovalRequestDate(LocalDateTime.now());
//        approvalDoc.setWhetherSavingApproval("N");
//        saveFirstApprovalLine(approvalDoc, user);
//
//        return approvalDocRepository.save(approvalDoc);
//    }

//    // 결재 문서 내용 추가 - 휴가 신청서
//    public void saveOnLeaveDoc(ApprovalDoc savedApprovalDoc){
//        OnLeave onLeave = new OnLeave();
//        onLeave.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
//        onLeave.setKindOfOnLeave("연차");
//        onLeave.setOnLeaveTitle("타이틀 테스트");
//        onLeave.setOnLeaveReason("개인 사유");
//        onLeave.setOnLeaveStartDate(new Date(124,1,19));
//        onLeave.setOnLeaveEndDate(new Date(124,1,21));
//
//        onLeaveRepository.save(onLeave);
//    }
//
//    // 결재 문서 내용 추가 - 연장근로 신청서
//    public void saveOverworkDoc(ApprovalDoc savedApprovalDoc){
//        Overwork overwork = new Overwork();
//        overwork.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
//        overwork.setKindOfOverwork("연장 근로");
//        overwork.setOverworkTitle("[개발1팀] 연장 근로 신청서");
//    }


    // 기안 문서 정보 저장 - 휴가 신청서
    @Transactional
public ApprovalDoc saveOnLeaveApprovalDoc(ApprovalDocDTO approvalDocDTO, User user) {
    log.info("[ApprovalService] saving doc info started =====");

    // 저장할 ApprovalDoc 객체 생성
    ApprovalDoc approvalDoc = modelMapper.map(approvalDocDTO, ApprovalDoc.class);
    approvalDoc.setApprovalForm("휴가신청서");

    // OnLeave 문서 저장 및 해당 문서의 제목 반환
    String onLeaveTitle = saveOnLeaveDoc(approvalDoc);

    // 사용자 정보 설정
    LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
    approvalDoc.setEmployeeCode(loginEmployee);

    // 결재 요청일 설정
    approvalDoc.setApprovalRequestDate(LocalDateTime.now());

    // 결재 여부 설정
    approvalDoc.setWhetherSavingApproval("N");

    // 첫 번째 결재 라인 저장
    saveFirstApprovalLine(approvalDoc, user);

    // ApprovalDoc 저장 후 반환
    return approvalDocRepository.save(approvalDoc);
}

    // 기안 문서 정보 저장 - 연장근로 신청서
    @Transactional
    public String saveOverworkApprovalDoc(OverworkDTO overworkDTO, User user) {
        ApprovalDoc approvalDoc = new ApprovalDoc();
        approvalDoc.setApprovalForm("연장근로신청서");

        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        approvalDoc.setEmployeeCode(loginEmployee);

        approvalDoc.setApprovalRequestDate(LocalDateTime.now());

        approvalDoc.setWhetherSavingApproval("N");

        approvalDoc.setApprovalTitle(overworkDTO.getOverworkTitle());

        saveFirstApprovalLine(approvalDoc, user);

        System.out.println("approvalDoc = " + approvalDoc);

        ApprovalDoc result = approvalDocRepository.save(approvalDoc);

        System.out.println("result = " + result);

        Overwork overwork = new Overwork();


        overwork.setApprovalDocCode(result.getApprovalDocCode());
        overwork.setOverworkTitle(overworkDTO.getOverworkTitle());
        overwork.setKindOfOverwork(overworkDTO.getKindOfOverwork());
        // 받은 문자열 형식의 날짜를 Date로 변환
        Date overworkDate = java.sql.Date.valueOf(LocalDate.parse(overworkDTO.getOverworkDate()));
        overwork.setOverworkDate(String.valueOf(overworkDate));

        // 받은 문자열 형식의 시간을 Time으로 변환
        Time overworkStartTime = java.sql.Time.valueOf(LocalTime.parse(overworkDTO.getOverworkStartTime()));
        overwork.setOverworkStartTime(String.valueOf(overworkStartTime));

        Time overworkEndTime = java.sql.Time.valueOf(LocalTime.parse(overworkDTO.getOverworkEndTime()));
        overwork.setOverworkEndTime(String.valueOf(overworkEndTime));
        overwork.setOverworkReason(overworkDTO.getOverworkReason());

        System.out.println("overwork = " + overwork);
        overworkRepository.save(overwork);

        return "연장근로 신청서 기안 성공";
    }

    // 결재 문서 내용 추가 - 연장근로 신청서
//    @Transactional
//    public String saveOverworkDoc(ApprovalDoc savedApprovalDoc){
//        Overwork overwork = new Overwork();
//        overwork.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
//        overwork.setOverworkTitle("[개발1팀] 20240221 연장 근무 신청서");
//        overwork.setKindOfOverwork("연장 근무");
//        overwork.setOverworkDate(new Date(124,1,21));
//        overwork.setOverworkStartTime(new Time(18,00,00));
//        overwork.setOverworkEndTime(new Time(19,30,00));
//        overwork.setOverworkReason("신규 버전 배포 ");
//
//        overworkRepository.save(overwork);
//
//        return overwork.getOverworkTitle();
//    }

    // 기안 문서 정보 저장 - SW 사용 신청서
    @Transactional
    public ApprovalDoc saveSWUseveApprovalDoc(ApprovalDocDTO approvalDocDTO, User user) {
        ApprovalDoc approvalDoc = modelMapper.map(approvalDocDTO, ApprovalDoc.class);
        approvalDoc.setApprovalForm("SW사용신청서");

        String SWUseTitle = saveSWDoc(approvalDoc);

        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        approvalDoc.setEmployeeCode(loginEmployee);

        approvalDoc.setApprovalRequestDate(LocalDateTime.now());

        approvalDoc.setWhetherSavingApproval("N");

        saveFirstApprovalLine(approvalDoc, user);

        return approvalDocRepository.save(approvalDoc);
    }

    // 기안 문서 정보 저장 - 외근/출장/재택근무 신청서
    @Transactional
    public ApprovalDoc saveWorkTypeApprovalDoc(ApprovalDocDTO approvalDocDTO, User user) {
        ApprovalDoc approvalDoc = modelMapper.map(approvalDocDTO, ApprovalDoc.class);
        approvalDoc.setApprovalForm("근무형태신청서");

        String workTypeTitle = saveWorkTypeDoc(approvalDoc);

        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        approvalDoc.setEmployeeCode(loginEmployee);

        approvalDoc.setApprovalRequestDate(LocalDateTime.now());

        approvalDoc.setWhetherSavingApproval("N");

        saveFirstApprovalLine(approvalDoc, user);

        return approvalDocRepository.save(approvalDoc);
    }

    // 결재 문서 내용 추가 - 휴가 신청서
    @Transactional
    public String saveOnLeaveDoc(ApprovalDoc savedApprovalDoc){
        // OnLeave 객체 생성 및 정보 설정
        OnLeave onLeave = new OnLeave();
        onLeave.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
        onLeave.setKindOfOnLeave("연차");
        onLeave.setOnLeaveTitle("타이틀 테스트");
        onLeave.setOnLeaveReason("개인 사유");
        onLeave.setOnLeaveStartDate(new Date(124,1,19));
        onLeave.setOnLeaveEndDate(new Date(124,1,21));

        // OnLeave 저장
        onLeaveRepository.save(onLeave);

        // 저장한 OnLeave 문서의 제목 반환
        return onLeave.getOnLeaveTitle();
    }

    // 결재 문서 내용 추가 - SW 사용 신청서
    @Transactional
    public String saveSWDoc(ApprovalDoc savedApprovalDoc){
        SoftwareUse softwareUse = new SoftwareUse();
        softwareUse.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
        softwareUse.setSoftwareTitle("[개발1팀] 신규 입사자 SW 요청");
        softwareUse.setKindOfSoftware("MS Office");
        softwareUse.setSoftwareReason("신규 입사자 업무 세팅");
        softwareUse.setSoftwareStartDate(new Date(124,2,1));

        softwareUseRepository.save(softwareUse);

        return softwareUse.getSoftwareTitle();
    }

    // 결재 문서 내용 추가 - 외근/출장/재택근무 신청서
    @Transactional
    public String saveWorkTypeDoc(ApprovalDoc savedApprovalDoc){
        WorkType workType = new WorkType();
        workType.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
        workType.setWorkTypeForm("재택근무");
        workType.setWorkTypeTitle("[개발1팀] 차주 재택근무 신청서");
        workType.setWorkTypeStartDate(new Date(124,2,4));
        workType.setWorkTypeEndDate(new Date(124,2,8));
        workType.setWorkTypePlace("WFH");
        workType.setWorkTypeReason("팀 재택근무 기간");

        workTypeRepository.save(workType);

        return workType.getWorkTypeTitle();
    }

    // 기안자 결재선 저장
    @Transactional
    public void saveFirstApprovalLine(ApprovalDoc savedApprovalDoc, User user) {
        log.info("[ApprovalService] saving first approval line started =====");
        ApprovalLine approvalLine = new ApprovalLine();
        approvalLine.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());

        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        approvalLine.setEmployeeCode((long) loginEmployee.getEmployeeCode());

        approvalLine.setApprovalProcessOrder(1L);
        approvalLine.setApprovalProcessStatus("기안");
        approvalLine.setApprovalProcessDate(LocalDateTime.now());
        approvalLine.setApprovalRejectedReason(null);
        approvalLineRepository.save(approvalLine);
    }

//    // 추가 결재선 저장
//    public void saveApprovalLines(ApprovalDoc savedApprovalDoc){
//        log.info("[ApprovalService] saving line info started =====");
//        AdditionalApprovalLine additionalApprovalLine = new AdditionalApprovalLine();
//        additionalApprovalLine.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
//
//        additionalApprovalLine.setEmployeeCode(32L);
//        additionalApprovalLine.setApprovalProcessOrder(2L);
//        additionalApprovalLine.setApprovalProcessStatus("대기");
//        additionalApprovalLine.setApprovalRejectedReason(null);
//
//        additionalApprovalLineRepository.save(additionalApprovalLine);
//    }

    // 추가 결재선 저장
    @Transactional
    public void saveApprovalLines(ApprovalDoc savedApprovalDoc, List<Long> additionalApprovers) {
        log.info("[ApprovalService] saving line info started =====");
        for (int i = 0; i < additionalApprovers.size(); i++) {
            AdditionalApprovalLine additionalApprovalLine = new AdditionalApprovalLine();
            additionalApprovalLine.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());

            additionalApprovalLine.setEmployeeCode(additionalApprovers.get(i));
            additionalApprovalLine.setApprovalProcessOrder((long) (i + 2)); // 첫 번째 결재자 이후부터 시작하기 위해 +2
            additionalApprovalLine.setApprovalProcessStatus("대기");
            additionalApprovalLine.setApprovalRejectedReason(null);

            additionalApprovalLineRepository.save(additionalApprovalLine);
        }
    }

    // 파일 첨부
//    @Transactional
//    public String uploadImgToDoc(Long approvalDocCode, MultipartFile file){
//
//        LocalDateTime now = LocalDateTime.now();
//        ApprovalAttached approvalAttached = approvalAttachedRepository.findByApprovalDocCode(approvalDocCode)
//                .orElseGet(() -> {
//                    ApprovalAttached tempDocImg = new ApprovalAttached()
//                            .setApprovalDocCode(approvalDocCode)
//                            .setApprovalAttachedDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
//                            .setWhetherDeletedApprovalAttached("N")
//                            .builder();
//                    approvalAttachedRepository.save(tempDocImg);
//                    return tempDocImg;
//                });
//        return "첨부 성공";
//        }

    @Transactional
    public void saveAttachement(ApprovalDoc savedApprovalDoc, MultipartFile file) {

        if(file != null) {
            String originalFileName = file.getOriginalFilename();

            try {
                String changedFileName = FileUploadUtils.saveFile(IMAGE_DIR, originalFileName, file);

            ApprovalAttached approvalAttached = new ApprovalAttached();
            approvalAttached.setApprovalDocCode(savedApprovalDoc.getApprovalDocCode());
            approvalAttached.setApprovalOgFile(originalFileName);
            approvalAttached.setApprovalChangedFile(changedFileName);
            approvalAttached.setWhetherDeletedApprovalAttached("N");

            approvalAttachedRepository.save(approvalAttached);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 대리 결재 지정
    @Transactional
    public String setRepresent(ApprovalRepresentDTO approvalRepresentDTO, User user) {
        // 위임자, 결재자 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        ApprovalRepresent represent = modelMapper.map(approvalRepresentDTO, ApprovalRepresent.class);

        System.out.println("loginEmployee = " + loginEmployee);

        // 결재자 지정
        ApprovalRepresent approvalRepresent = new ApprovalRepresent();
        approvalRepresent.setAssignee(loginEmployee);
        approvalRepresent.setRepresentative(32L);
        approvalRepresent.setStartDate(new Date(124,1,14));
        approvalRepresent.setEndDate(new Date(124,1,17));
        approvalRepresent.setRepresentStatus("N");

        System.out.println("approvalRepresent = " + approvalRepresent);

        approvalRepresentRepository.save(approvalRepresent);

        return "지정 성공";
    }

    // 사원 정보 조회
    public ApprovalEmployeeDTO findUserDetail(Long employeeCode) {
        log.info("findUser Start =========");

        ApprovalEmployee approvalEmployee = approvalEmployeeRepository.findByEmployeeCode(employeeCode);
        ApprovalEmployeeDTO approvalEmployeeDTO = modelMapper.map(approvalEmployee, ApprovalEmployeeDTO.class);

        log.info("findUser End ========");

        return approvalEmployeeDTO;
    }

    // 로그인한 사원 조회
    public ApprovalEmployee approvalUserInfo(User user) {

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        System.out.println("loginEmployee====" + loginEmployee);
        ApprovalEmployee approvalEmployee = approvalEmployeeRepository.findByEmployeeCode((long) loginEmployee.getEmployeeCode());

        return approvalEmployee;
    }


    // 상신한 문서 조회
    public List<ApprovalDoc> findApprovalDocsByEmployeeCode(User user) {
        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<ApprovalDoc> outboxDocList = approvalDocRepository.findByEmployeeCode(loginEmployee);

        return outboxDocList;
    }

    // 결재 대기 문서 조회
    public List<ApprovalDoc> inboxDocListByEmployeeCode(User user){
        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        // 문서 코드 목록 가져오기
        List<Long> inboxDocCodeList = approvalDocRepository.inboxDocListByEmployeeCode(Long.valueOf(loginEmployee.getEmployeeCode()));

        // 문서 코드 목록으로 ApprovalDoc 정보 가져오기
        List<ApprovalDoc> inboxDocs = new ArrayList<>();
        for (Long approvalDocCode : inboxDocCodeList) {
            ApprovalDoc approvalDoc = approvalDocRepository.findByApprovalDocCode(approvalDocCode);
            if(approvalDoc != null) {
                inboxDocs.add(approvalDoc);
            }
        }
        return inboxDocs;
    }

    // 수신함 - 결재 완료함
    public List<ApprovalDoc> inboxFinishedListByEmployeeCode(User user){

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        System.out.println("loginEmployee = " + loginEmployee);

        // 해당 사용자가 지정된 결재선의 문서 코드 목록 가져오기
        List<Long> inboxFinishedCodeList = approvalDocRepository.inboxFinishedListbyEmployeeCode(Long.valueOf(loginEmployee.getEmployeeCode()));
        System.out.println("inboxFinishedCodeList = " + inboxFinishedCodeList);

        // 문서 코드 목록으로 ApprovalDoc 정보 가져오기
        List<ApprovalDoc> finishedDocs = new ArrayList<>();
        for (Long approvalDocCode : inboxFinishedCodeList) {
            ApprovalDoc approvalDoc = approvalDocRepository.findByApprovalDocCode(approvalDocCode);
            if(approvalDoc != null) {
                finishedDocs.add(approvalDoc);
            }
        }

        return finishedDocs;
    }

    // 결재하기
    @Transactional
    public String approvement(Long approvalDocCode, User user) {

        // 결재 대상 조회
        Long approvalSubject = additionalApprovalLineRepository.approvalSubjectEmployeeCode(approvalDocCode);

        System.out.println("approvalSubject ========== " + approvalSubject);
        System.out.println("approvalSubject.getClass() = " + approvalSubject.getClass());

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        Long employeeCode = (long) loginEmployee.getEmployeeCode();

        System.out.println("loginEmployee.getEmployeeCode() = " + loginEmployee.getEmployeeCode());
        System.out.println("loginEmployee.getClass() = " + loginEmployee.getClass());

        System.out.println("employeeCode ======== " + employeeCode);

        // 대리 결재 여부 확인
        ApprovalRepresent approvalRepresent = approvalRepresentRepository.findByRepresentative(employeeCode);

        System.out.println("approvalRepresent = " + approvalRepresent);
        System.out.println("approvalRepresent.getRepresentStatus() = " + approvalRepresent.getRepresentStatus());
        System.out.println("approvalRepresent.getAssignee() = " + approvalRepresent.getAssignee());

        if (approvalRepresent.getRepresentStatus().equals("Y") && approvalRepresent.getRepresentative() == loginEmployee.getEmployeeCode()) {
            AdditionalApprovalLine representApprovalLine = additionalApprovalLineRepository.findByApprovalDocCodeAndEmployeeCodeAndApprovalProcessStatus(approvalDocCode, (long) approvalRepresent.getAssignee().getEmployeeCode(), "대기");

            if (representApprovalLine != null) {
                // 결재 상태 업데이트
                representApprovalLine.setApprovalProcessDate(LocalDateTime.now());
                representApprovalLine.setApprovalProcessStatus("결재");
                representApprovalLine.setEmployeeCode(approvalRepresent.getRepresentative());
                additionalApprovalLineRepository.save(representApprovalLine);
                return "결재 성공";
            }
        }

        if (!approvalSubject.equals(employeeCode)) {
            return "결재 대상이 아닙니다.";
        }

        AdditionalApprovalLine additionalApprovalLine = additionalApprovalLineRepository.findByApprovalDocCodeAndEmployeeCodeAndApprovalProcessStatus(approvalDocCode, employeeCode, "대기");

        if (additionalApprovalLine != null) {
            // 결재 상태 업데이트
            additionalApprovalLine.setApprovalProcessDate(LocalDateTime.now());
            additionalApprovalLine.setApprovalProcessStatus("결재");
            additionalApprovalLineRepository.save(additionalApprovalLine);
            return "결재 성공";
        }

        return "결재 대상이 아닙니다.";

    }

    // 반려하기
    @Transactional
    public String rejection(Long approvalDocCode, User user) {

        // 반려 대상 조회
        Long approvalSubject = additionalApprovalLineRepository.approvalSubjectEmployeeCode(approvalDocCode);

        System.out.println("approvalSubject ========== " + approvalSubject);
        System.out.println("approvalSubject.getClass() = " + approvalSubject.getClass());

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        Long employeeCode = (long) loginEmployee.getEmployeeCode();

        System.out.println("loginEmployee.getEmployeeCode() = " + loginEmployee.getEmployeeCode());
        System.out.println("loginEmployee.getClass() = " + loginEmployee.getClass());

        System.out.println("employeeCode ======== " + employeeCode);

        // 반려 대상과 로그인한 사용자 employeeCode 비교
        if (!approvalSubject.equals(employeeCode)) {
            return "반려 대상이 아닙니다.";
        }

        // 대기 상태인 모든 approvalLine 조회
        List<Long> pendingApprovalLines = additionalApprovalLineRepository.findPendingApprovalLines(approvalDocCode);

        // 각 approvalLine의 상태를 반려로 변경
        for (Long approvalLineCode : pendingApprovalLines) {
            Optional<AdditionalApprovalLine> optionalApprovalLine = additionalApprovalLineRepository.findById(approvalLineCode);
            optionalApprovalLine.ifPresent(approvalLine -> {
                approvalLine.setApprovalProcessDate(LocalDateTime.now());
                approvalLine.setApprovalProcessStatus("반려");
                additionalApprovalLineRepository.save(approvalLine);
            });
        }

        return "반려 성공";
    }

    // 상신 문서 회수
    @Transactional
    public String retrieval(Long approvalDocCode, User user) {
        // 문서 정보 가져오기
        ApprovalDoc approvalDoc = approvalDocRepository.findById(approvalDocCode).get();
        System.out.println("approvalDoc.getEmployeeCode().getEmployeeCode() = " + approvalDoc.getEmployeeCode().getEmployeeCode());
        Long employeeCode = (long) approvalDoc.getEmployeeCode().getEmployeeCode();
        System.out.println("employeeCode = " + employeeCode);

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        Long loginemployeeCode = (long) loginEmployee.getEmployeeCode();
        System.out.println("loginemployeeCode = " + loginemployeeCode);

        // 해당 문서 employeeCode가 로그인한 사용자와 일치하는지 확인
        if (!employeeCode.equals(loginemployeeCode)) {
            return "회수 대상이 아닙니다.";
        }

        // 해당 문서의 모든 approvalLine 가져오기
        List<AdditionalApprovalLine> approvalLines = additionalApprovalLineRepository.findByApprovalDocCode(approvalDocCode);

        // 모든 approvalLine의 상태가 "기안" 또는 "대기"인지 확인
        for (AdditionalApprovalLine approvalLine : approvalLines) {
            String status = approvalLine.getApprovalProcessStatus();
            if (!status.equals("기안") && !status.equals("대기")) {
                return "미결 문서가 아닙니다.";
            }
        }

        // approvalLine의 상태를 회수로 변경
        for (AdditionalApprovalLine approvalLine : approvalLines) {
            approvalLine.setApprovalProcessStatus("회수");
            approvalLine.setApprovalProcessDate(LocalDateTime.now());
            additionalApprovalLineRepository.save(approvalLine);
        }

        return "회수 성공";
    }
//
//    public void saveApprovalInfo(ApprovalDoc approvalDoc) {
//        switch (approvalDoc.getApprovalForm()) {
//            case "휴가신청서":
//                List<OnLeave> onLeaveInfo = onLeaveRepository.findByApprovalDocCode(approvalDoc);
//                if (!onLeaveInfo.isEmpty()) {
//                    OnLeave onLeave = onLeaveInfo.get(0);
//                    approvalDoc.setApprovalTitle(onLeave.getApprovalTitle());
//                }
//                break;
//            case "sw사용신청서":
//                List<SoftwareUse> softwareInfo = softwareUseRepository.findByApprovalDocCode(approvalDoc);
//                if (!softwareInfo.isEmpty()) {
//                    SoftwareUse softwareUse = softwareInfo.get(0);
//                    approvalDoc.setApprovalTitle(softwareUse.getApprovalTitle());
//                }
//                break;
//            case "근무형태신고서":
//                List<WorkType> workTypeInfo = workTypeRepository.findByApprovalDocCode(approvalDoc);
//                if (!workTypeInfo.isEmpty()) {
//                    WorkType workType = workTypeInfo.get(0);
//                    approvalDoc.setApprovalTitle(workType.getApprovalTitle());
//                }
//                break;
//            case "추가근무신청서":
//                List<Overwork> overworkInfo = overworkRepository.findByApprovalDocCode(approvalDoc);
//                if (!overworkInfo.isEmpty()) {
//                    Overwork overwork = overworkInfo.get(0);
//                    approvalDoc.setApprovalTitle(overwork.getApprovalTitle());
//                }
//                break;
//            default:
//                break;
//        }
//    }

    // 결재 진행 중인 문서 조회
    public List<ApprovalDoc> onProcessInOutbox(User user) {

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<ApprovalDoc> outboxDocList = approvalDocRepository.findByEmployeeCode(loginEmployee);

        // 결재 상태 중에 대기가 존재하는 문서 리스트 조회
        List<ApprovalDoc> onProcessDocList = new ArrayList<>();
        for(ApprovalDoc approvalDoc : outboxDocList) {
            List<Long> pendingApprovalLines = additionalApprovalLineRepository.findPendingApprovalLines(approvalDoc.getApprovalDocCode());
            if(!pendingApprovalLines.isEmpty()) {
                onProcessDocList.add(approvalDoc);
            }
        }
        return onProcessDocList;
    }

    // 결재 완료 문서 조회
    public List<ApprovalDoc> finishedInOutbox(User user) {

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<ApprovalDoc> outboxDocList = approvalDocRepository.findByEmployeeCode(loginEmployee);

        List<ApprovalDoc> finishedDocListInOutbox = new ArrayList<>();

        // 해당 사용자의 결재 상신 문서 리스트를 순회하며 쿼리를 통해 검색한 결과와 비교하여 결과 리스트에 추가
        for (ApprovalDoc approvalDoc : outboxDocList) {
            List<Long> finishedDocCodes = additionalApprovalLineRepository.finishedInOutboxDocCode((long) loginEmployee.getEmployeeCode());
            if (finishedDocCodes.contains(approvalDoc.getApprovalDocCode())) {
                finishedDocListInOutbox.add(approvalDoc);
            }
        }

        return finishedDocListInOutbox;
    }

    // 반려 문서 조회
    public List<ApprovalDoc> rejectedInOutbox(User user) {

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<ApprovalDoc> outboxDocList = approvalDocRepository.findByEmployeeCode(loginEmployee);

        // 결재 상태 중에 반려가 존재하는 문서 리스트 조회
        List<ApprovalDoc> rejectedDocList = new ArrayList<>();
        for(ApprovalDoc approvalDoc : outboxDocList) {
            List<Long> rejectedApprovalLines = additionalApprovalLineRepository.findRejectedApprovalLines(approvalDoc.getApprovalDocCode());
            if(!rejectedApprovalLines.isEmpty()) {
                rejectedDocList.add(approvalDoc);
            }
        }
        return rejectedDocList;
    }

    // 회수 문서 조회
    public List<ApprovalDoc> retrievedInOutbox(User user) {

        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        // 해당 사용자의 결재 상신 문서 리스트 조회
        List<ApprovalDoc> outboxDocList = approvalDocRepository.findByEmployeeCode(loginEmployee);

        // 결재 상태 중에 회수가 존재하는 문서 리스트 조회
        List<ApprovalDoc> retrievedDocList = new ArrayList<>();
        for(ApprovalDoc approvalDoc : outboxDocList) {
            List<Long> retrievedApprovalLines = additionalApprovalLineRepository.findRetrievedApprovalLines(approvalDoc.getApprovalDocCode());
            if(!retrievedApprovalLines.isEmpty()) {
                retrievedDocList.add(approvalDoc);
            }
        }
        return retrievedDocList;
    }

    // 임시 저장
    @Transactional
    public ApprovalDoc temporarySaveApprovalDoc(ApprovalDocDTO approvalDocDTO, User user) {

        ApprovalDoc approvalDoc = modelMapper.map(approvalDocDTO, ApprovalDoc.class);
        approvalDoc.setApprovalForm("임시저장문서");

        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);
        approvalDoc.setEmployeeCode(loginEmployee);

        approvalDoc.setApprovalRequestDate(LocalDateTime.now());
        approvalDoc.setWhetherSavingApproval("Y");

        return approvalDocRepository.save(approvalDoc);
    }

    // 임시 저장 리스트 조회
    public List<ApprovalDoc> findSavedDocsByEmployeeCode(User user) {
        // 로그인한 사용자의 정보 가져오기
        LoginEmployee loginEmployee = modelMapper.map(user, LoginEmployee.class);

        // 해당 사용자의 결재 문서 중 임시 저장이 'Y'인 문서 조회
        return approvalDocRepository.findByEmployeeCodeAndWhetherSavingApproval(loginEmployee, "Y");
    }

    // 결재 문서 내용 추가 - 휴가 신청서

    // 휴가 일수 차감
}