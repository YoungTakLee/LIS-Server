package com.green.service.impl;

import com.green.dto.ResponseDto;
import com.green.entity.BannerFileEntity;
import com.green.enums.ResponseStatus;
import com.green.repository.BannerFileRepository;
import com.green.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    private static final String DCS = "upload-dir/dcs";
    private static final String MAG = "upload-dir/mag";
    private static final String BANNER = "upload-dir/banner";
    private String[] savedPath = {DCS, MAG, BANNER};
    private BannerFileRepository bannerFileRepository;

    FileServiceImpl(BannerFileRepository bannerFileRepository) {
        this.bannerFileRepository = bannerFileRepository;
    }

    @PostConstruct
    void checkPath() {
        for (String path : savedPath) {
            if (!Files.exists(Paths.get(path))) {
                try {
                    Files.createDirectories(Paths.get(path));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public ResponseDto uploadFiles(String equipType, MultipartFile[] files) {
        ResponseDto responseDto = new ResponseDto();
        try {
            for (MultipartFile file : files) {
                file.getName();
                String fileName = getFileName(equipType, file.getOriginalFilename());
                Path p = Paths.get(fileName);
                Files.copy(file.getInputStream(), p);
                responseDto.setStatus(ResponseStatus.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            Arrays.stream(files).forEach(uploadFile -> {
                Path p = Paths.get(getFileName(equipType, uploadFile.getOriginalFilename()));
                if (Files.exists(p)) {
                    try {
                        Files.delete(p);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
        }
        return responseDto;
    }

    private String getFileName(String equipType, String orgFileName) {
        String filePath = null;
        if (equipType.equalsIgnoreCase("dcs")) {
            filePath = Paths.get(DCS).toString();
        } else if (equipType.equalsIgnoreCase("banner")) {
            filePath = Paths.get(BANNER).toString();
        } else if (equipType.equalsIgnoreCase("mag")) {
            filePath = Paths.get(MAG).toString();
        }

        return filePath + "/" + orgFileName;
    }

    @Override
    public Resource loadAsResource(String type, String filename) throws Exception {
        try {
            Path path;
            if (BANNER.contains(type)) {
                path = Paths.get(BANNER + "/" + filename);
            } else {
                path = null;
            }
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new Exception("Could not read file: " + filename, e);
        }
    }

    @Override
    public String getOrgFileName(String type, String fileName) {
        String originName = "";
        if (BANNER.contains(type)) {
            Optional<BannerFileEntity> bannerFileEntity = bannerFileRepository.findBannerFileEntityBySavedFileName(fileName);
            if(bannerFileEntity.isPresent()) {
                originName = bannerFileEntity.get().getOriginalFileName();
            }
        }
        return originName;
    }
}
