
package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddPartRequest;
import com.example.Interactive.Electronic.Journal.dto.response.PartResponse;
import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.service.PartService;
import com.example.Interactive.Electronic.Journal.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/part")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;
    private final JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<List<PartResponse>> addPart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<AddPartRequest> requests)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, requests.get(0).getObjectId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<PartResponse> responses = partService.addPart(requests);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get")
    public ResponseEntity<PartResponse> getPart(@RequestParam Long partId) {
        PartResponse response = partService.getPart(partId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-by-object")
    public ResponseEntity<List<PartResponse>> getAllByObject(@RequestParam Long objectId) {
        List<PartResponse> responses = partService.getAllPartsByObject(objectId);

        return ResponseEntity.ok(responses);
    }

}
