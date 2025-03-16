package org.project.pals.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.project.pals.dto.AuthRequestDto;
import org.project.pals.model.Role;
import org.project.pals.model.User;
import org.project.pals.model.enums.RolesType;
import org.project.pals.repository.RoleRepository;
import org.project.pals.service.UserService;
import org.project.pals.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@Tag(name = "Авторизации/Регистрация", description = "Api для работы с регистрацией/авторизацией пользователей")
public class AuthController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    public AuthController(UserService userService, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    @Operation(summary = "Создать нового пользователя", description = "Принимает AuthRequestDto, возвращает http ответ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "403", description = "У вас не прав для регистрации"),
            @ApiResponse(responseCode = "400", description = "Неверные данные переданы (Bad Request)")
    })
    public ResponseEntity<String> regNewUser(
            @Parameter(description = "AuthResponseDTO",
                    required = true)
            @RequestBody AuthRequestDto authRequest) {
        User user = new User(authRequest.username(), authRequest.password(), Collections.singleton(
                roleRepository.getRoleByRole(RolesType.ROLE_USER)
        ));
        userService.addNewUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
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
            return ResponseEntity.ok("Logging successfully");

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Неправильный логин или пароль");
        }
    }
}
