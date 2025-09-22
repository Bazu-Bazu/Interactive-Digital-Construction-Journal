package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.RegisterUserRequest;
import com.example.Interactive.Electronic.Journal.dto.response.UserResponse;
import com.example.Interactive.Electronic.Journal.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<UserResponse> addAdmin(@RequestBody RegisterUserRequest request) {
        UserResponse response = adminService.addAdmin(request);

        return ResponseEntity.ok(response);
    }

}
