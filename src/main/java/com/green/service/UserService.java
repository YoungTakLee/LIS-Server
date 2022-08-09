package com.green.service;

import com.green.dto.LoginUserDto;
import com.green.dto.ResponseDto;

import java.util.Map;

public interface UserService {
    ResponseDto registerCmsUser (LoginUserDto loginUserDto) ;

    Map<String, Object> loginCmsAdmin (LoginUserDto loginUserDto);
}
