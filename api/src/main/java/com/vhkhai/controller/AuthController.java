package com.vhkhai.controller;

import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.account.AccountResponseDto;
import com.vhkhai.service.AccountService;
import com.vhkhai.utils.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
     private final AccountService accountService;

     @PostMapping("/register")
     public ResponseEntity register(@RequestBody AccountRequestDto accountRequestDto) {
          AccountResponseDto responseDto = accountService.create(accountRequestDto);
          return new RestResponse<>()
                    .withData(responseDto)
                    .withStatus(200)
                    .withMessage("Account created successfully")
                    .buildHttpResponseEntity();
     }

     @PostMapping("/login")
     public ResponseEntity login(@RequestBody AccountRequestDto accountRequestDto) {
          return new RestResponse<>()
                  .withData(Map.of("token", accountService.login(accountRequestDto)))
                  .withStatus(200)
                  .withMessage("Login successfully")
                  .buildHttpResponseEntity();
     }

}
