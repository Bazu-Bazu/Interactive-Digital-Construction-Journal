package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.AddRemarkRequest;
import com.example.Interactive.Electronic.Journal.dto.response.RemarkResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.entity.Remark;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import com.example.Interactive.Electronic.Journal.repository.RemarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RemarkService {

    private final RemarkRepository remarkRepository;
    private final ConstructionObjectRepository constructionObjectRepository;

    @Transactional
    public RemarkResponse addRemark(AddRemarkRequest request) {
        ConstructionObject object = constructionObjectRepository.findById(request.getObjectId())
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));

        Remark remark = new Remark();
        remark.setCoordinates(request.getCoordinates());
        remark.setDescription(request.getDescription());
        remark.setCreatedAt(LocalDateTime.now());
        remark.setFixed(false);
        remark.setObject(object);
        remarkRepository.save(remark);

        return buildRemarkResponse(remark);
    }

    private RemarkResponse buildRemarkResponse(Remark remark) {
        return RemarkResponse.builder()
                .id(remark.getId())
                .coordinates(remark.getCoordinates())
                .createdAt(remark.getCreatedAt())
                .description(remark.getDescription())
                .fixed(remark.getFixed())
                .urls(remark.getUrls())
                .build();
    }

}
