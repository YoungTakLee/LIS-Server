package com.green.service.impl;

import com.green.dto.LoginUserDto;
import com.green.dto.ResponseDto;
import com.green.entity.CmsUserEntity;
import com.green.enums.ResponseStatus;
import com.green.repository.CmsUserRepository;
import com.green.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private CmsUserRepository cmsUserRepository;
    private PasswordEncoder passwordEncoder;

    UserServiceImpl(CmsUserRepository cmsUserRepository, PasswordEncoder passwordEncoder) {
        this.cmsUserRepository = cmsUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> loginCmsAdmin(LoginUserDto loginUserDto) {
        Map<String, Object> resultMap = new HashMap<>();
        CmsUserEntity byLoginId = cmsUserRepository.findByLoginId(loginUserDto.getAdminID());
        if (passwordEncoder.matches(loginUserDto.getPassword(), byLoginId.getPassword())) {
            resultMap.put("login", "Y");
        } else {
            resultMap.put("login", "N");
            resultMap.put("reason", "password do not matched");
        }
        return resultMap;
    }

    @Override
    public ResponseDto registerCmsUser(LoginUserDto loginUserDto) {
        ResponseDto responseDto = new ResponseDto();
        CmsUserEntity userEntity = CmsUserEntity.builder()
                .loginId(loginUserDto.getAdminID())
                .password(passwordEncoder.encode(loginUserDto.getPassword()))
                .build();
        try {
            if(null != cmsUserRepository.findByLoginId(loginUserDto.getAdminID())) {
                responseDto.setStatus(ResponseStatus.ALREADY_REGISTERED);
            } else {
                cmsUserRepository.save(userEntity);
                responseDto.setStatus(ResponseStatus.SUCCESS);
            }
            responseDto.setResponseBody(userEntity);
        } catch (Exception e) {
            logger.error("message",e);
        }
        return responseDto;
    }
}
