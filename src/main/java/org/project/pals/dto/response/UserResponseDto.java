package org.project.pals.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.project.pals.model.user.Role;

import java.util.Set;

@Schema(name = "UserResponseDto", description = "DTO, которая возвращает данные пользователя")
public record UserResponseDto(
        @Schema(description = "Имя пользователя")
        String username,
        @Schema(description = "email пользователя")
        String email,
        @Schema(description = "Реальное имя пользователя")
        String name,
        @Schema(description = "Роли пользователя")
        Set<Role> roles)
 {
}
