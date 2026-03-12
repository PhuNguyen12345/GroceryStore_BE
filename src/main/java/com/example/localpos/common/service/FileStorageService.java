package com.example.localpos.common.service;

import com.example.localpos.common.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    FileUploadResponse uploadImage(MultipartFile file, String folder);
    FileUploadResponse replaceImage(MultipartFile newFile, String oldFileUrl, String folder);
    void deleteFile(String fileUrl);
}
