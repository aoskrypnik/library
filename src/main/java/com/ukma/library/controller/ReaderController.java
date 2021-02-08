package com.ukma.library.controller;

import com.ukma.library.dto.UserWithConfPassDto;
import com.ukma.library.model.User;
import com.ukma.library.repository.UserRepository;
import com.ukma.library.service.ReaderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Resource
    private ReaderService readerService;

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody UserWithConfPassDto user){
        User resUser = readerService.register(user);
        return ResponseEntity.ok(resUser);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return readerService.getUser(username);
    }
}
