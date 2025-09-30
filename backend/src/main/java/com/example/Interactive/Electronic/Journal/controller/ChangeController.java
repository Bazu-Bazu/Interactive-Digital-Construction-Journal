package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddChangeRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ChangeResponse;
import com.example.Interactive.Electronic.Journal.entity.Change;
import com.example.Interactive.Electronic.Journal.exception.ChangeException;
import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.repository.ChangeRepository;
import com.example.Interactive.Electronic.Journal.service.ChangeService;
import com.example.Interactive.Electronic.Journal.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/change")
@RequiredArgsConstructor
public class ChangeController {

    private final ChangeService changeService;
    private final JwtService jwtService;
    private final ChangeRepository changeRepository;

    @PostMapping("/add")
    public ResponseEntity<ChangeResponse> addChange(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AddChangeRequest request)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, request.getObjectId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ChangeResponse response = changeService.addChange(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/accept")
    public ResponseEntity<ChangeResponse> acceptChange(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long changeId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        Change change = changeRepository.findById(changeId)
                .orElseThrow(() -> new ChangeException("Change not found."));

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, change.getObject().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ChangeResponse response = changeService.acceptChange(changeId);

        return ResponseEntity.ok(response);
    }

}
