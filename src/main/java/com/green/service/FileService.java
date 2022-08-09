package com.green.service;

import com.green.dto.ResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String getOrgFileName(String type, String fileName);

    ResponseDto uploadFiles(String type, MultipartFile[] files);

    Resource loadAsResource(String type, String filename) throws Exception;
}
