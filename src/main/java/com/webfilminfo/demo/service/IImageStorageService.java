package com.webfilminfo.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImageStorageService {
    String storeFile(MultipartFile file);
}
