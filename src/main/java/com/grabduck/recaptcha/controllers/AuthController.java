package com.grabduck.recaptcha.controllers;

import com.grabduck.recaptcha.dtos.ReCaptchaResponseDto;
import com.grabduck.recaptcha.dtos.RegisterDto;
import com.grabduck.recaptcha.services.ReCaptchaApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {
    @Autowired
    private ReCaptchaApiClient reCaptchaApiClient;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(RegisterDto registerDto) {
        log.info("{}", registerDto);

        ReCaptchaResponseDto reCaptchaResponse = reCaptchaApiClient.verify(registerDto.getRecaptchaResponse());
        log.info("{}", reCaptchaResponse);

        if (!reCaptchaResponse.isSuccess()) {
            throw new RuntimeException();
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
