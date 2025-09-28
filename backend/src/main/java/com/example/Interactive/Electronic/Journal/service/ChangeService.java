package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddChangeRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ChangeResponse;
import com.example.Interactive.Electronic.Journal.entity.Change;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.entity.Part;
import com.example.Interactive.Electronic.Journal.exception.ChangeException;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.exception.PartException;
import com.example.Interactive.Electronic.Journal.repository.ChangeRepository;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import com.example.Interactive.Electronic.Journal.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeService {

    private final ChangeRepository changeRepository;
    private final PartRepository partRepository;
    private final ConstructionObjectRepository constructionObjectRepository;

    public ChangeResponse addChange(AddChangeRequest request) {
        Part part = partRepository.findById(request.getPartId())
                .orElseThrow(() -> new PartException("Part not found."));

        ConstructionObject object = constructionObjectRepository.findById(request.getObjectId())
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));

        Change change = new Change();
        change.setDescription(request.getDescription());
        change.setAccepted(false);
        change.setType(request.getType());
        change.setProposedStartDate(request.getProposedStartDate());
        change.setProposedEndDate(request.getProposedEndDate());
        change.setPart(part);
        change.setObject(object);
        changeRepository.save(change);

        return buildChangeResponse(change);
    }

    public ChangeResponse acceptChange(Long changeId) {
        Change change = changeRepository.findById(changeId)
                .orElseThrow(() -> new ChangeException("Change not found."));

        Long partId = change.getPart().getId();
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new PartException("Part not found."));

        if (change.getProposedStartDate() != null && change.getProposedEndDate() != null) {
            part.setStartDate(change.getProposedStartDate());
            part.setEndDate(change.getProposedEndDate());
        }
        partRepository.save(part);

        change.setAccepted(true);
        changeRepository.save(change);

        return buildChangeResponse(change);
    }

    private ChangeResponse buildChangeResponse(Change change) {
        return ChangeResponse.builder()
                .id(change.getId())
                .description(change.getDescription())
                .accepted(change.getAccepted())
                .type(change.getType())
                .proposedStartDate(change.getProposedStartDate())
                .proposedEndDate(change.getProposedEndDate())
                .objectId(change.getObject().getId())
                .partId(change.getPart().getId())
                .build();
    }

}
