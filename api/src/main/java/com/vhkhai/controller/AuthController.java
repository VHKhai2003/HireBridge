package com.vhkhai.controller;

import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.token.RefreshTokenRequestDto;
import com.vhkhai.dto.token.TokenRequestDto;
import com.vhkhai.service.AuthService;
import com.vhkhai.utils.RestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
     private final AuthService authService;

     @PostMapping("/login")
     public ResponseEntity login(@RequestBody AccountRequestDto accountRequestDto) {
          return new RestResponse<>()
                  .withData(authService.login(accountRequestDto))
                  .withStatus(200)
                  .withMessage("Login successfully")
                  .buildHttpResponseEntity();
     }

     @PostMapping("/logout")
     public ResponseEntity register(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
          authService.logout(tokenRequestDto);
          return new RestResponse<>()
                  .withStatus(200)
                  .withMessage("logout successfully")
                  .buildHttpResponseEntity();
     }

     @PostMapping("/refresh-token")
        public ResponseEntity refreshToken(@Valid @RequestBody RefreshTokenRequestDto requestDto) {
            return new RestResponse<>()
                    .withData(authService.refreshToken(requestDto))
                    .withStatus(200)
                    .withMessage("Refresh token successfully")
                    .buildHttpResponseEntity();
        }


}
