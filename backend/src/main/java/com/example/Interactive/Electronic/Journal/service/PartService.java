package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddPartRequest;
import com.example.Interactive.Electronic.Journal.dto.response.PartResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.entity.Part;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.exception.PartException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import com.example.Interactive.Electronic.Journal.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final ConstructionObjectRepository constructionObjectRepository;

    public PartResponse addPart(AddPartRequest request) {
        ConstructionObject object = constructionObjectRepository.findById(request.getObjectId())
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));

        Part part = new Part();
        part.setName(request.getName());
        part.setDescription(request.getDescription());
        part.setObject(object);
        part.setStartDate(request.getStartDate());
        part.setEndDate(request.getEndDate());
        part.setDone(request.getDone());
        partRepository.save(part);

        return buildPartResponse(part);
    }

    public PartResponse getPart(Long partId) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new PartException("Part not found."));

        return buildPartResponse(part);
    }

    public List<PartResponse> getAllPartsByObject(Long objectId) {
        List<Part> parts = partRepository.findAllByObjectId(objectId);

        return parts.stream()
                .map(this::buildPartResponse)
                .toList();
    }

    private PartResponse buildPartResponse(Part part) {
        return PartResponse.builder()
                .objectId(part.getObject().getId())
                .name(part.getName())
                .description(part.getDescription())
                .startDate(part.getStartDate())
                .endDate(part.getEndDate())
                .done(part.getDone())
                .build();
    }

}
