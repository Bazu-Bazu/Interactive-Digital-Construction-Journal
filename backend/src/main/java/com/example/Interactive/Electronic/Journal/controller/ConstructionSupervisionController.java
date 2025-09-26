package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddSupervisionRequest;
import com.example.Interactive.Electronic.Journal.dto.response.SupervisionResponse;
import com.example.Interactive.Electronic.Journal.service.ConstructionSupervisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supervision")
@RequiredArgsConstructor
public class ConstructionSupervisionController {

    private final ConstructionSupervisionService constructionSupervisionService;

    @PostMapping("/add")
    public ResponseEntity<SupervisionResponse> addSupervision(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddSupervisionRequest request)
    {
        String inspectorEmail = userDetails.getUsername();

        SupervisionResponse response = constructionSupervisionService.addSupervision(inspectorEmail, request);

        return ResponseEntity.ok(response);
    }

}
