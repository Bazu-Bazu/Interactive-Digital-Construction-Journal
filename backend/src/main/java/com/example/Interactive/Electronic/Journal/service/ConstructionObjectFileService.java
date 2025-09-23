package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.response.ObjectFileResponse;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObject;
import com.example.Interactive.Electronic.Journal.entity.ConstructionObjectFile;
import com.example.Interactive.Electronic.Journal.exception.ConstructionObjectException;
import com.example.Interactive.Electronic.Journal.exception.FileNotFoundException;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectFileRepository;
import com.example.Interactive.Electronic.Journal.repository.ConstructionObjectRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        objectFile.setUrl("/files/" + fileId);

        ConstructionObject object = constructionObjectRepository.findById(constructionObjectId)
                .orElseThrow(() -> new ConstructionObjectException("Object not found."));
        object.setOpenActionUrl(objectFile.getUrl());
        constructionObjectRepository.save(object);

        constructionObjectFileRepository.save(objectFile);

        return ObjectFileResponse.builder()
                .url(objectFile.getUrl())
                .build();
    }

    public GridFsResource getFileByUrl(String url) {
        ConstructionObjectFile fileMetadata = constructionObjectFileRepository.findByUrl(url)
                .orElseThrow(() -> new FileNotFoundException("File metadata not found."));

        GridFSFile gridFSFile = gridFsTemplate.findOne(
                new Query(Criteria.where("filename").is(fileMetadata.getFileName())));

        return gridFsTemplate.getResource(gridFSFile);
    }

    public ConstructionObjectFile getFileMetadataByUrl(String url) {
        ConstructionObjectFile fileMetadata = constructionObjectFileRepository.findByUrl(url)
                .orElseThrow(() -> new FileNotFoundException("File metadata not found."));

        return fileMetadata;
    }

}
