package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.ActivateObjectRequest;
import com.example.Interactive.Electronic.Journal.dto.request.AddObjectRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ObjectResponse;
import com.example.Interactive.Electronic.Journal.service.ConstructionObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/object")
@RequiredArgsConstructor
public class ConstructionObjectController {

    private final ConstructionObjectService constructionObjectService;

    @PostMapping("/add")
    public ResponseEntity<ObjectResponse> addObject(@RequestBody AddObjectRequest request) {
        ObjectResponse response = constructionObjectService.addObject(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ObjectResponse> getObject(@RequestParam Long objectId) {
        ObjectResponse response = constructionObjectService.getObject(objectId);

        return ResponseEntity.ok(response);
    }

    @GetMapping ("/get-n")
    ResponseEntity<List<ObjectResponse>> getNObjects(@RequestParam Integer count) {
        List<ObjectResponse> response = constructionObjectService.getNObjects(count);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    ResponseEntity<ObjectResponse> activate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ActivateObjectRequest request)
    {
        String customerEmail = userDetails.getUsername();
        ObjectResponse response = constructionObjectService.activateObject(customerEmail, request);

        return ResponseEntity.ok(response);
    }

}
