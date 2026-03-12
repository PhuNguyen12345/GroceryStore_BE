package com.example.localpos.common.service.impl;

import com.example.localpos.common.dto.response.FileUploadResponse;
import com.example.localpos.common.service.FileStorageService;
import com.example.localpos.config.FileStorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalFileStorageServiceImpl implements FileStorageService {
    private final FileStorageProperties fileStorageProperties;

    private static final Set<String> ALLOWED_IMAGE_TYPES = Set.of(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/webp"
    );

    private static final Set<String> ALLOWED_FOLDERS = Set.of(
            "images"
    );

    @Override
    public FileUploadResponse uploadImage(MultipartFile file, String folder) {
        validateFolder(folder);
        validateImage(file);
        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = getExtension(originalFileName);
            String storedFileName = UUID.randomUUID() + extension;

            Path uploadRootPath = Paths.get(fileStorageProperties.getUploadDir())
                    .toAbsolutePath()
                    .normalize();

            Path folderPath = uploadRootPath.resolve(folder).normalize();
            Files.createDirectories(folderPath);

            Path targetPath = folderPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            String url = fileStorageProperties.getPublicUrlPrefix() + "/" + folder + "/" + storedFileName;

            return FileUploadResponse.builder()
                    .fileName(storedFileName)
                    .originalFileName(originalFileName)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .url(url)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Không thể tải ảnh lên", e);
        }
    }

    @Override
    public FileUploadResponse replaceImage(MultipartFile newFile, String oldFileUrl, String folder) {
        FileUploadResponse uploaded = uploadImage(newFile, folder);

        if (oldFileUrl != null && !oldFileUrl.isBlank()) {
            deleteFile(oldFileUrl);
        }

        return uploaded;
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }

        try {
            String publicUrlPrefix = fileStorageProperties.getPublicUrlPrefix();

            if (!fileUrl.startsWith(publicUrlPrefix + "/")) {
                throw new IllegalArgumentException("Đường dẫn file không hợp lệ");
            }

            String relativePath = fileUrl.substring(publicUrlPrefix.length() + 1);

            Path uploadRootPath = Paths.get(fileStorageProperties.getUploadDir())
                    .toAbsolutePath()
                    .normalize();

            Path filePath = uploadRootPath.resolve(relativePath).normalize();

            if (!filePath.startsWith(uploadRootPath)) {
                throw new IllegalArgumentException("Đường dẫn file không hợp lệ");
            }

            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Không thể xóa ảnh", e);
        }
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Ảnh tải lên không được để trống");
        }

        if (!ALLOWED_IMAGE_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Chỉ hỗ trợ ảnh JPG, JPEG, PNG, WEBP");
        }
    }

    private void validateFolder(String folder) {
        if (folder == null || folder.isBlank()) {
            throw new IllegalArgumentException("Thư mục lưu ảnh không được để trống");
        }

        if (!ALLOWED_FOLDERS.contains(folder)) {
            throw new IllegalArgumentException("Thư mục lưu ảnh không hợp lệ");
        }
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }


}
