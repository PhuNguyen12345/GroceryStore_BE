package com.example.localpos.config;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
private final FileStorageProperties fileStorageProperties;

@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String publicUrlPrefix = fileStorageProperties.getPublicUrlPrefix();
    String uploadDir = fileStorageProperties.getUploadDir();
    registry.addResourceHandler(publicUrlPrefix + "/**").addResourceLocations("file:" + uploadDir + "/");
}
}
