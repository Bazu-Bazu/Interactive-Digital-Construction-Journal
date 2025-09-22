package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddObjectRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ObjectResponse;
import com.example.Interactive.Electronic.Journal.service.ConstructionObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
