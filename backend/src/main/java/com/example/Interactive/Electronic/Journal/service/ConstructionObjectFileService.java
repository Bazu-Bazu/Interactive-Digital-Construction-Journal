package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.response.ObjectFileResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObjectFile;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectFileRepository;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstructionObjectFileService {

    private final GridFsTemplate gridFsTemplate;
    private final ConstructionObjectFileRepository constructionObjectFileRepository;
    private final ConstructionObjectRepository constructionObjectRepository;

    @Transactional
    public ObjectFileResponse addFile(MultipartFile file, Long constructionObjectId) throws IOException {
        ObjectId fileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );

        ConstructionObjectFile objectFile = new ConstructionObjectFile();
        objectFile.setObjectId(constructionObjectId);
        objectFile.setFileName(file.getOriginalFilename());
        objectFile.setContentType(file.getContentType());
        objectFile.setSize(file.getSize());
        objectFile.setUrl("/object-files/" + fileId);

        ConstructionObject object = constructionObjectRepository.findById(constructionObjectId)
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));
        object.setOpenActionUrl(objectFile.getUrl());
        constructionObjectRepository.save(object);

        constructionObjectFileRepository.save(objectFile);

        return ObjectFileResponse.builder()
                .url(objectFile.getUrl())
                .build();
    }

    public List<ObjectFileResponse> getFileByObject(Long objectId) {
        List<ConstructionObjectFile> files = constructionObjectFileRepository.findAllByObjectId(objectId);

        return files.stream()
                .map(this::buildObjectFileResponse)
                .toList();
    }

    public ResponseEntity<Resource> downloadFile(String fileId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(
                new Query(Criteria.where("_id").is(new ObjectId(fileId))));

        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private ObjectFileResponse buildObjectFileResponse(ConstructionObjectFile file) {
        return ObjectFileResponse.builder()
                .id(file.getId())
                .name(file.getFileName())
                .url(file.getUrl())
                .contentType(file.getContentType())
                .size(file.getSize())
                .objectId(file.getObjectId())
                .build();
    }

}
