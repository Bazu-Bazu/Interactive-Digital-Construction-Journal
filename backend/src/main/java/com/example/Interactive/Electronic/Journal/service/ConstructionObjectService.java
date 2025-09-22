package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddObjectRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ObjectResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private ObjectResponse buildObjectResponse(ConstructionObject object) {
        return ObjectResponse.builder()
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
