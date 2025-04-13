package com.vhkhai.controller;

import com.vhkhai.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller1 {

    private final AccountService accountService;

    @GetMapping("/hello")
    public Object hello() {
        return "Hello world";
    }
}
