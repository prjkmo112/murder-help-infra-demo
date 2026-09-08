package io.github.prjkmo112.murderhelpinfrademo.domain.file.dto;

import lombok.Getter;

@Getter
public class FileUploadResponse {

    private final String key;
    private final String url;

    public FileUploadResponse(String key, String url) {
        this.key = key;
        this.url = url;
    }
}
