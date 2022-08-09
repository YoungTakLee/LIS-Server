package com.green.controller;

import com.green.dto.ResponseDto;
import com.green.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/file/upload/{type}")
    public ResponseDto uploadFile(@PathVariable("type") String type, @RequestPart("files") MultipartFile[] files) {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto = fileService.uploadFiles(type, files);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error occur : {}", e.getMessage());
        }
        return responseDto;
    }

    @GetMapping("/files/banner/{filename}")
    public ResponseEntity<?> serveFileForBanner(@PathVariable String filename) {
        try {
            Resource file = fileService.loadAsResource("banner", filename);
            String orgFileName = fileService.getOrgFileName("banner", filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + orgFileName + "\"").body(file);
        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
