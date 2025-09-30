package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.ActivateObjectRequest;
import com.example.Interactive.Electronic.Journal.dto.request.AddObjectRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ObjectResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.entity.ConstructionSupervision;
import com.example.Interactive.Electronic.Journal.entity.User;
import com.example.Interactive.Electronic.Journal.enums.Role;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import com.example.Interactive.Electronic.Journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionObjectService {

    private final ConstructionObjectRepository constructionObjectRepository;
    private final UserRepository userRepository;

    @Transactional
    public ObjectResponse addObject(AddObjectRequest request) {
        ConstructionObject object = new ConstructionObject();
        object.setName(request.getName());
        object.setCoordinates(request.getCoordinates());
        object.setAddress(request.getAddress());
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

    public List<ObjectResponse> getNObjects(Integer count) {
        List<ConstructionObject> objectsList = constructionObjectRepository.findTopNByOrderByStartDateAsc(count);

        return objectsList.stream()
                .map(this::buildObjectResponse)
                .toList();
    }

    public List<ObjectResponse> getBySupervisionId(Long supervisionId) {
        List<ConstructionObject> objects = constructionObjectRepository.findAllBySupervisionId(supervisionId);

        return objects.stream()
                .map(this::buildObjectResponse)
                .toList();
    }

    public List<ObjectResponse> getByForeman(Long foremanId) {
        List<ConstructionObject> objects = constructionObjectRepository.findAllByForemanId(foremanId);

        return objects.stream()
                .map(this::buildObjectResponse)
                .toList();
    }

    @Transactional
    public ObjectResponse activateObject(String customerEmail, ActivateObjectRequest request) {
        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        User foreman = userRepository.findById(request.getForemanId())
                .orElseThrow(() -> new UsernameNotFoundException("USer not found."));
        if (foreman.getRole() != Role.ROLE_FOREMAN) {
            throw new UsernameNotFoundException("User is not foreman.");
        }

        ConstructionObject object = constructionObjectRepository.findById(request.getObjectId())
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));

        ConstructionSupervision supervision = customer.getCustomerSupervision();

        object.setCustomer(customer);
        object.setForeman(foreman);
        object.setSupervision(supervision);
        object.setActivated(request.getActivate());
        constructionObjectRepository.save(object);

        customer.setObjectId(object.getId());
        userRepository.save(customer);

        foreman.setObjectId(object.getId());
        userRepository.save(foreman);

        return buildObjectResponse(object);
    }

    private ObjectResponse buildObjectResponse(ConstructionObject object) {
        return ObjectResponse.builder()
                .id(object.getId())
                .name(object.getName())
                .coordinates(object.getCoordinates())
                .address(object.getAddress())
                .startDate(object.getStartDate())
                .activated(object.getActivated())
                .customerId(object.getCustomer() != null ? object.getCustomer().getId() : null)
                .foremanId(object.getForeman() != null ? object.getForeman().getId() : null)
                .inspectorId(object.getInspector() != null ? object.getInspector().getId() : null)
                .supervisionId(object.getSupervision() != null ? object.getSupervision().getId() : null)
                .build();
    }

}
