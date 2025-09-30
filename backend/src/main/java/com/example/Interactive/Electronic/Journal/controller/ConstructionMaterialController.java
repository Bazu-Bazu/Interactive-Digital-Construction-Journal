package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddMaterialRequest;
import com.example.Interactive.Electronic.Journal.dto.response.MaterialResponse;
import com.example.Interactive.Electronic.Journal.service.ConstructionMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class ConstructionMaterialController {

    private final ConstructionMaterialService constructionMaterialService;

    @PostMapping("/add")
    public ResponseEntity<List<MaterialResponse>> addMaterials(@RequestBody List<AddMaterialRequest> requests) {
        List<MaterialResponse> response = constructionMaterialService.addMaterials(requests);

        return ResponseEntity.ok(response);
    }

}
