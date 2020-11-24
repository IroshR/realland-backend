package com.teamtrace.realland.service;

import com.teamtrace.realland.api.request.FileDeleteRequest;
import com.teamtrace.realland.api.response.FileUploadResponse;
import com.teamtrace.realland.api.response.UpdateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileUploadResponse uploadImageS3(MultipartFile multipartFile, int merchantId, int primaryId, String category);

    UpdateResponse deleteImageS3(FileDeleteRequest request);
}
