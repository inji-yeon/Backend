package com.wittypuppy.backend.Employee.controller;


import com.wittypuppy.backend.common.dto.ResponseDTO;
import com.wittypuppy.backend.Employee.dto.EmployeeDTO;
import com.wittypuppy.backend.Employee.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }


//    회원가입에 관한 건 어떻게 하지 상태값 없애고 퇴직날짜가 null이면 회사를 다니고 있는 상태이고 퇴직날짜가 존재하면 탈퇴한 상태
    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody EmployeeDTO employeeDTO){
        // 멤버의 기본 상태값 설정
//        employeeDTO.setEmployeeRetirementDate();
        String joinDateStr = "2024-01-31 16:33:33";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.CREATED, "회원가입 성공", authService.signup(employeeDTO)));

    }
}