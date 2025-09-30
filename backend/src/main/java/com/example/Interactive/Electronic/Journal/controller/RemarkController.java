package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddRemarkRequest;
import com.example.Interactive.Electronic.Journal.dto.response.RemarkResponse;
import com.example.Interactive.Electronic.Journal.entity.Remark;
import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.exception.RemarkException;
import com.example.Interactive.Electronic.Journal.repository.RemarkRepository;
import com.example.Interactive.Electronic.Journal.service.RemarkService;
import com.example.Interactive.Electronic.Journal.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/remark")
@RequiredArgsConstructor
public class RemarkController {

    private final RemarkService remarkService;
    private final JwtService jwtService;
    private final RemarkRepository remarkRepository;

    @PostMapping("/add")
    public ResponseEntity<RemarkResponse> addRemark(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AddRemarkRequest request)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, request.getObjectId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        RemarkResponse response = remarkService.addRemark(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/fixed")
    public ResponseEntity<RemarkResponse> fixedRemark(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long remarkId)
    {
        Remark remark = remarkRepository.findById(remarkId)
                .orElseThrow(() -> new RemarkException("Remark not found."));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, remark.getObject().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        RemarkResponse response = remarkService.fixedRemark(remarkId);

        return ResponseEntity.ok(response);
    }

}
