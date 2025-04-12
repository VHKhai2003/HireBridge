package com.vhkhai.controller;

import com.vhkhai.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller1 {

    private final AccountRepository accountRepository;
    @GetMapping("/hello")
    public Object hello() {
        return accountRepository.findAll();
    }
}
