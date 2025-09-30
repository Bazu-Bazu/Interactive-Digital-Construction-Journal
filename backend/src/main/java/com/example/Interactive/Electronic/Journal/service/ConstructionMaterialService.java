package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddMaterialRequest;
import com.example.Interactive.Electronic.Journal.dto.response.MaterialResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionMaterial;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionMaterialRepository;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionMaterialService {

    private final ConstructionMaterialRepository constructionMaterialRepository;
    private final ConstructionObjectRepository constructionObjectRepository;

    public List<MaterialResponse> addMaterials(List<AddMaterialRequest> requests) {
        ConstructionObject object = constructionObjectRepository.findById(requests.get(0).getObjectId())
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));

        List<ConstructionMaterial> newMaterials = requests.stream()
                .map(request -> {
                    ConstructionMaterial material = new ConstructionMaterial();
                    material.setName(request.getName());
                    material.setDescription(request.getDescription());
                    material.setAmount(request.getAmount());
                    material.setCost(request.getCost());
                    material.setObject(object);

                    return material;
                })
                .toList();

        constructionMaterialRepository.saveAll(newMaterials);

        return newMaterials.stream()
                .map(this::buildMaterialResponse)
                .toList();
    }

    private MaterialResponse buildMaterialResponse(ConstructionMaterial material) {
        return MaterialResponse.builder()
                .id(material.getId())
                .name(material.getName())
                .description(material.getDescription())
                .amount(material.getAmount())
                .cost(material.getCost())
                .objectId(material.getObject().getId())
                .build();
    }

}
