package com.example.localpos.common.dto.response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileUploadResponse {
    private String fileName;
    private String originalFileName;
    private String contentType;
    private Long size;
    private String url;
}
