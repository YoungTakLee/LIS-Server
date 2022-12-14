package com.green.controller;

import com.green.dto.LoginUserDto;
import com.green.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView indexPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @PostMapping(value = "/user/register")
    public ResponseEntity registerUser(@RequestBody LoginUserDto loginUserDto) {
        return new ResponseEntity<>(userService.registerCmsUser(loginUserDto), HttpStatus.OK);
    }

    @GetMapping(value = "/user/login")
    @ResponseBody
    public ResponseEntity<?> loginCms(LoginUserDto loginUserDto) {
        System.out.println("loginUserDto = " + loginUserDto);
        if (loginUserDto.getAdminID() == null) {
            return new ResponseEntity<>("required adminID parameters.", HttpStatus.BAD_REQUEST);
        }

        if (loginUserDto.getPassword() == null) {
            return new ResponseEntity<>("required password parameters.", HttpStatus.BAD_REQUEST);
        }

        try {

            return new ResponseEntity<>(userService.loginCmsAdmin(loginUserDto), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("message : {}", e.getMessage());
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/hbcheck")
    public ResponseEntity<?> heartbeatCheck() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/powerOff")
    public ResponseEntity<?> powerOff() {
        Thread th = new Thread(() -> {
            Runtime rt = Runtime.getRuntime();
            try {
                Thread.sleep(3000L);
                rt.exec("shutdown -s -t 1");
            } catch (Exception ex) {
                log.error("message : {}", ex);
            }
        });

        th.start();
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
}
