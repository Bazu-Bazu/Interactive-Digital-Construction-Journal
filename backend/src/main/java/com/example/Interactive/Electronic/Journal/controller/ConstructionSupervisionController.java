package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddSupervisionRequest;
import com.example.Interactive.Electronic.Journal.dto.response.SupervisionResponse;
import com.example.Interactive.Electronic.Journal.entity.User;
import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.repository.UserRepository;
import com.example.Interactive.Electronic.Journal.service.ConstructionSupervisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/supervision")
@RequiredArgsConstructor
public class ConstructionSupervisionController {

    private final ConstructionSupervisionService constructionSupervisionService;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<SupervisionResponse> addSupervision(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddSupervisionRequest request)
    {
        String inspectorEmail = userDetails.getUsername();

        SupervisionResponse response = constructionSupervisionService.addSupervision(inspectorEmail, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-customers")
    public ResponseEntity<SupervisionResponse> addCustomersToSupervision(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam List<Long> customerIds,
            @RequestParam Long supervisionId)
    {
        String email = userDetails.getUsername();
        User inspector = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        if (!Objects.equals(inspector.getInspectorSupervision().getId(), supervisionId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        SupervisionResponse response = constructionSupervisionService
                .addCustomersToSupervision(customerIds, supervisionId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-inspectors")
    public ResponseEntity<SupervisionResponse> addInspectorsToSupervision(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam List<Long> inspectorIds,
            @RequestParam Long supervisionId)
    {
        String email = userDetails.getUsername();
        User inspector = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        if (!Objects.equals(inspector.getInspectorSupervision().getId(), supervisionId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        SupervisionResponse response = constructionSupervisionService
                .addInspectorsToSupervision(inspectorIds, supervisionId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<SupervisionResponse> getSupervisionByName(@RequestParam String name) {
        SupervisionResponse response = constructionSupervisionService.getSupervisionByName(name);

        return ResponseEntity.ok(response);
    }

}
