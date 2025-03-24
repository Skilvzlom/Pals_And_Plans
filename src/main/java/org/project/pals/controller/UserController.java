package org.project.pals.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.project.pals.dto.request.RegistrationRequestDto;
import org.project.pals.dto.response.UserResponseDto;
import org.project.pals.mapper.UserMapper;
import org.project.pals.model.user.User;
import org.project.pals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//TODO Add logger for each end-point
@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "API для работы с пользователями")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping()
    @Operation(summary = "Создать нового пользователя (Первоначальные права User)", description = "Принимает RegistrationRequestDto, возвращает http ответ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "403", description = "У вас не прав для регистрации"),
            @ApiResponse(responseCode = "400", description = "Неверные данные переданы (Bad Request)")
    })
    public ResponseEntity<String> newUser(
            @Parameter(description = "RegistrationResponseDTO",
                    required = true)
            @RequestBody RegistrationRequestDto requestDto)
    {
        User user = userMapper.registrationRequestDtoToUser(requestDto);
        userService.addNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Получить всех пользователей", description = "Только для ролей 'Админ', 'Кодер', 'Модератор'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все хорошо"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав")
    })
    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'CODER', 'MODERATOR')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("#username == authentication.name or hasAnyRole('ADMIN', 'CODER', 'MODERATOR')")
    @GetMapping("/{username}")
    @Operation(summary = "Получить пользователя по username",
            description = "Либо для ролей 'Админ', 'Кодер', 'Модератор', либо если пользователь запрашивает сам себя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все хорошо"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PreAuthorize("#username == authentication.name or hasAnyRole('ADMIN', 'CODER', 'MODERATOR')")
    @DeleteMapping("/{username}")
    @Operation(summary = "Удалить пользователя по username",
            description = "Либо для ролей 'Админ', 'Кодер', 'Модератор', либо если пользователь запрашивает сам себя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все хорошо"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#user.username == authentication.name or hasAnyRole('ADMIN', 'CODER', 'MODERATOR')")
    @PutMapping()
    @Operation(summary = "Обновить данные пользователя",
            description = "Либо для ролей 'Админ', 'Кодер', 'Модератор', либо если пользователь запрашивает сам себя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все хорошо"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }
}
