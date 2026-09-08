package io.github.prjkmo112.murderhelpinfrademo.domain.file.service;

import io.github.prjkmo112.murderhelpinfrademo.domain.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.prefix}")
    private String prefix;

    public FileUploadResponse upload(MultipartFile file) {
        String key = prefix + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String url = s3Client.utilities().getUrl(b -> b.bucket(bucket).key(key)).toString();
            return new FileUploadResponse(key, url);
        } catch (IOException e) {
            throw new UncheckedIOException("파일 업로드 실패: " + file.getOriginalFilename(), e);
        }
    }
}