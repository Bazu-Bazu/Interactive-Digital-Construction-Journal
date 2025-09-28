package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.response.RemarkFileResponse;
import com.example.Interactive.Electronic.Journal.entity.Remark;
import com.example.Interactive.Electronic.Journal.entity.RemarkFile;
import com.example.Interactive.Electronic.Journal.exception.RemarkException;
import com.example.Interactive.Electronic.Journal.repository.RemarkFileRepository;
import com.example.Interactive.Electronic.Journal.repository.RemarkRepository;
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
public class RemarkFileService {

    private final GridFsTemplate gridFsTemplate;
    private final RemarkFileRepository remarkFileRepository;
    private final RemarkRepository remarkRepository;

    @Transactional
    public List<RemarkFileResponse> addRemarkFiles(List<MultipartFile> files, Long remarkId) {
        Remark remark = remarkRepository.findById(remarkId)
                .orElseThrow(() -> new RemarkException("Remark not found."));

        List<RemarkFile> remarkFiles = files.stream()
                .map(file -> {
                    try {
                        ObjectId fileId = gridFsTemplate.store(
                                file.getInputStream(),
                                file.getOriginalFilename(),
                                file.getContentType()
                        );

                        RemarkFile remarkFile = new RemarkFile();
                        remarkFile.setRemarkId(remarkId);
                        remarkFile.setFileName(file.getOriginalFilename());
                        remarkFile.setContentType(file.getContentType());
                        remarkFile.setSize(file.getSize());
                        remarkFile.setUrl("/remark-files/" + fileId);

                        return remarkFile;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        List<RemarkFile> savedFiles = remarkFileRepository.saveAll(remarkFiles);

        List<String> urls = savedFiles.stream()
                .map(RemarkFile::getUrl)
                .toList();

        remark.getUrls().addAll(urls);
        remarkRepository.save(remark);

        return savedFiles.stream()
                .map(this::buildRemarkFileResponse)
                .toList();
    }

    public List<RemarkFileResponse> getAllFilesOfRemark(Long remarkId) {
        List<RemarkFile> remarks = remarkFileRepository.findAllByRemarkId(remarkId);

        return remarks.stream()
                .map(this::buildRemarkFileResponse)
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

    private RemarkFileResponse buildRemarkFileResponse(RemarkFile file) {
        return RemarkFileResponse.builder()
                .id(file.getId())
                .name(file.getFileName())
                .url(file.getUrl())
                .contentType(file.getContentType())
                .size(file.getSize())
                .remarkId(file.getRemarkId())
                .build();
    }

}
