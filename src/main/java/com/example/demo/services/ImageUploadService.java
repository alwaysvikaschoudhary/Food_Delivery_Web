package com.example.demo.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        try {
            // Check if user still has the placeholder credentials
            if ("your_cloud_name".equals(cloudinary.config.cloudName) || 
                "your_api_key".equals(cloudinary.config.apiKey)) {
                throw new IllegalStateException("Cloudinary credentials are not configured! Please update your .env file.");
            }

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "products"));
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while uploading image to Cloudinary", e);
        }
    }
}
