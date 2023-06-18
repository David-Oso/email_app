package com.mail.mini_mailing_app.spring.boot.services.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String upload(MultipartFile image);
}
