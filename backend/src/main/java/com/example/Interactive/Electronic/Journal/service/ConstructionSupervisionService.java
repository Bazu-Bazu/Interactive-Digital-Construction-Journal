package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddSupervisionRequest;
import com.example.Interactive.Electronic.Journal.dto.response.SupervisionResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionSupervision;
import com.example.Interactive.Electronic.Journal.entity.User;
import com.example.Interactive.Electronic.Journal.enums.Role;
import com.example.Interactive.Electronic.Journal.repository.ConstructionSupervisionRepository;
import com.example.Interactive.Electronic.Journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstructionSupervisionService {

    private final ConstructionSupervisionRepository constructionSupervisionRepository;
    private final UserRepository userRepository;

    public SupervisionResponse addSupervision(String inspectorEmail, AddSupervisionRequest request) {
        User inspector = userRepository.findByEmail(inspectorEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Inspector not found."));

        if (inspector.getRole() != Role.ROLE_INSPECTOR) {
            throw new UsernameNotFoundException("User is not inspector.");
        }

        ConstructionSupervision supervision = new ConstructionSupervision();
        supervision.setName(request.getName());
        supervision.getInspectors().add(inspector);
        constructionSupervisionRepository.save(supervision);

        inspector.setSupervision(supervision);
        userRepository.save(inspector);

        return buildSupervisionResponse(supervision);
    }

    private SupervisionResponse buildSupervisionResponse(ConstructionSupervision supervision) {
        return SupervisionResponse.builder()
                .id(supervision.getId())
                .name(supervision.getName())
                .inspectorIds(supervision.getInspectors().stream()
                        .map(User::getId)
                        .toList())
                .customerIds(supervision.getCustomers().stream()
                        .map(User::getId)
                        .toList())
                .build();
    }

}
