package io.github.prjkmo112.murderhelpinfrademo.domain.file.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import io.github.prjkmo112.murderhelpinfrademo.domain.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Template s3Template;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.prefix}")
    private String prefix;

    public FileUploadResponse upload(MultipartFile file) {
        String key = prefix + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = ObjectMetadata.builder()
                    .contentType(file.getContentType())
                    .build();
            S3Resource resource = s3Template.upload(bucket, key, inputStream, metadata);

            return new FileUploadResponse(key, resource.getURL().toString());
        } catch (IOException e) {
            throw new UncheckedIOException("파일 업로드 실패: " + file.getOriginalFilename(), e);
        }
    }
}
