package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddObjectRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ObjectResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionObjectService {

    private final ConstructionObjectRepository constructionObjectRepository;

    @Transactional
    public ObjectResponse addObject(AddObjectRequest request) {
        ConstructionObject object = new ConstructionObject();
        object.setName(request.getName());
        object.setCoordinates(request.getCoordinates());
        object.setStartDate(request.getStartDate());
        object.setActivated(false);
        constructionObjectRepository.save(object);

        return buildObjectResponse(object);
    }

    public ObjectResponse getObject(Long objectId) {
        ConstructionObject object = constructionObjectRepository.findById(objectId)
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));

        return buildObjectResponse(object);
    }

    public List<ObjectResponse> getNObjects(int count) {
        List<ConstructionObject> objectsList = constructionObjectRepository.findTopNByOrderByStartDateAsc(count);

        return objectsList.stream()
                .map(this::buildObjectResponse)
                .toList();
    }

    private ObjectResponse buildObjectResponse(ConstructionObject object) {
        return ObjectResponse.builder()
                .id(object.getId())
                .name(object.getName())
                .coordinates(object.getCoordinates())
                .startDate(object.getStartDate())
                .activated(object.getActivated())
                .customerId(object.getCustomer() != null ? object.getCustomer().getId() : null)
                .foremanId(object.getForeman() != null ? object.getForeman().getId() : null)
                .inspectorId(object.getInspector() != null ? object.getInspector().getId() : null)
                .build();
    }

}
