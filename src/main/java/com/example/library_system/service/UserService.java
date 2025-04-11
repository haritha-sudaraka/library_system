package com.example.library_system.service;

import com.example.library_system.config.SecurityConfig;
import com.example.library_system.constants.ApplicationConstants;
import com.example.library_system.dto.ResponseDto;
import com.example.library_system.dto.SignUpRequestDto;
import com.example.library_system.entity.User;
import com.example.library_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public ResponseDto register(SignUpRequestDto signUpRequestDto) {
        User user = User.builder()
                .name(signUpRequestDto.getName())
                .email(signUpRequestDto.getEmail())
                .password(securityConfig.passwordEncoder().encode(signUpRequestDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
        user = userRepository.save(user);
        return ResponseDto.builder().message(ApplicationConstants.USER_CREATED + user.getId()).build();
    }
}
