package com.ukma.library.controller;

import com.ukma.library.dto.JwtResponseDto;
import com.ukma.library.dto.UserWithConfPassDto;
import com.ukma.library.model.User;
import com.ukma.library.service.ReaderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Resource
    private ReaderService readerService;

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponseDto register(@RequestBody UserWithConfPassDto user){
        return readerService.register(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return readerService.getUserByUsername(username);
    }
}
