package com.wittypuppy.backend.Employee.controller;


import com.wittypuppy.backend.common.dto.ResponseDTO;
import com.wittypuppy.backend.Employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmployeeController {

    private final EmployeeService EmployeeService;  // service class 생성 이후 코드를 작성

    public EmployeeController(EmployeeService memberService) {
        this.EmployeeService = memberService;
    }

    // 조회 , /members/{memberId}
//    @ApiOperation(value = "회원 조회 요청", notes = "회원 한명이 조회됩니다.", tags = { "MemberController" })
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ResponseDTO> selectMyEmployeeInfo(@PathVariable String employeeId){

        log.info("[EmployeeController]  selectMyEmployeeInfo   Start =============== ");
        log.info("[EmployeeController]  selectMyEmployeeInfo   {} ====== ", employeeId);

        log.info("[EmployeeController]  selectMyEmployeeInfo   End ================= ");
        return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK, "조회 성공", EmployeeService.selectMyInfo(employeeId)));
    }
}
