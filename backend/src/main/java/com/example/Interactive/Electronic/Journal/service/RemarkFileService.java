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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
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
                        remarkFile.setUrl("/files/" + fileId);

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
                .map(file -> RemarkFileResponse.builder()
                        .url(file.getUrl())
                        .build())
                .toList();
    }

    public List<GridFsResource> getAllFilesOfRemark(Long remarkId) {
        List<RemarkFile> files = remarkFileRepository.findAllByRemarkId(remarkId);

        return files.stream()
                .map(file -> {
                    GridFSFile gridFSFile = gridFsTemplate.findOne(
                            new Query(Criteria.where("filename").is(file.getFileName())));

                    return gridFsTemplate.getResource(gridFSFile);
                })
                .toList();
    }

}
