package com.example.library_system.controller;

import com.example.library_system.dto.ResponseDto;
import com.example.library_system.dto.SignUpRequestDto;
import com.example.library_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseDto register(@RequestBody @Valid SignUpRequestDto requestDto) {
        return userService.register(requestDto);
    }
}
