package org.project.pals.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.project.pals.dto.request.AuthRequestDto;
import org.project.pals.model.user.User;
import org.project.pals.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

//TODO, Add logger for each end-point
@RestController
@RequestMapping("/auth")
@Tag(name = "API для авторизации", description = "Api для работы с авторизацией/unАвторизацией пользователей")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизовать нового пользователя", description = "Принимает AuthRequestDto, возвращает token в Cookie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь авторизован"),
            @ApiResponse(responseCode = "400", description = "Неправильно переданы данные пользователя")
    })
    public ResponseEntity<?> login (@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.username(), authRequestDto.password())
            );
            String token = jwtUtil.generateToken(((User) authentication.getPrincipal()).getUsername());
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(3600*24);
            response.addCookie(cookie);
            return ResponseEntity.ok(Collections.singletonMap("message", "logging successful"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Bad Credentials"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
