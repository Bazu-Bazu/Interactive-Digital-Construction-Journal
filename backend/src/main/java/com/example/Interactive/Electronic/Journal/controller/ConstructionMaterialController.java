package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddMaterialRequest;
import com.example.Interactive.Electronic.Journal.dto.response.MaterialResponse;
import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.service.ConstructionMaterialService;
import com.example.Interactive.Electronic.Journal.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class ConstructionMaterialController {

    private final ConstructionMaterialService constructionMaterialService;
    private final JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<List<MaterialResponse>> addMaterials(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<AddMaterialRequest> requests) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, requests.get(0).getObjectId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<MaterialResponse> response = constructionMaterialService.addMaterials(requests);

        return ResponseEntity.ok(response);
    }

}
