package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddSupervisionRequest;
import com.example.Interactive.Electronic.Journal.dto.response.SupervisionResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionSupervision;
import com.example.Interactive.Electronic.Journal.entity.User;
import com.example.Interactive.Electronic.Journal.enums.Role;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionSupervisionRepository;
import com.example.Interactive.Electronic.Journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionSupervisionService {

    private final ConstructionSupervisionRepository constructionSupervisionRepository;
    private final UserRepository userRepository;

    @Transactional
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

        inspector.setInspectorSupervision(supervision);
        userRepository.save(inspector);

        return buildSupervisionResponse(supervision);
    }

    @Transactional
    public SupervisionResponse addCustomersToSupervision(List<Long> customerIds, Long supervisionId) {
        ConstructionSupervision supervision = constructionSupervisionRepository.findById(supervisionId)
                .orElseThrow(() -> new ConstructionObjectException("Supervision not found."));

        List<User> customers = userRepository.findAllById(customerIds);
        List<User> filterCustomers = customers.stream()
                .filter(customer -> customer.getRole() == Role.ROLE_CUSTOMER)
                .toList();

        supervision.getCustomers().addAll(filterCustomers);
        constructionSupervisionRepository.save(supervision);

        filterCustomers.forEach(customer -> customer.setCustomerSupervision(supervision));
        userRepository.saveAll(filterCustomers);

        return buildSupervisionResponse(supervision);
    }

    @Transactional
    public SupervisionResponse addInspectorsToSupervision(List<Long> inspectorIds, Long supervisionId) {
        ConstructionSupervision supervision = constructionSupervisionRepository.findById(supervisionId)
                .orElseThrow(() -> new ConstructionObjectException("Supervision not found."));

        List<User> inspectors = userRepository.findAllById(inspectorIds);
        List<User> filterInspectors = inspectors.stream()
                .filter(inspector -> inspector.getRole() == Role.ROLE_INSPECTOR)
                .toList();

        supervision.getInspectors().addAll(filterInspectors);
        constructionSupervisionRepository.save(supervision);

        filterInspectors.forEach(inspector -> inspector.setInspectorSupervision(supervision));
        userRepository.saveAll(filterInspectors);

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
