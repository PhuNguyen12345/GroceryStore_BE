package com.example.localpos.common.controller;

import com.example.localpos.common.constants.ApiPaths;
import com.example.localpos.common.dto.request.FileDeleteRequest;
import com.example.localpos.common.dto.response.FileUploadResponse;
import com.example.localpos.common.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping(ApiPaths.FileCtrl.FILE)
@RequiredArgsConstructor
public class FileController {
    private final FileStorageService fileStorageService;

    private static final String IMAGE_FOLDER = "images";

    @PostMapping("/upload-image")
    public ResponseEntity<FileUploadResponse> uploadImage(
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(fileStorageService.uploadImage(file, IMAGE_FOLDER));
    }

    @PutMapping("/replace-image")
    public ResponseEntity<FileUploadResponse> replaceImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam String oldFileUrl
    ) {
        return ResponseEntity.ok(fileStorageService.replaceImage(file, oldFileUrl, IMAGE_FOLDER));
    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<String> deleteImage(@RequestBody FileDeleteRequest request) {
        fileStorageService.deleteFile(request.getFileUrl());
        return ResponseEntity.ok("Xóa ảnh thành công");
    }
}
