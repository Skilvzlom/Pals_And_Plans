package org.project.pals.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO объект для авторизации", name = "AuthResponseDto")
public record AuthRequestDto(
        @Schema(description = "Имя пользователя")
        String username,
        @Schema(description = "Пароль пользователя")
        String password) {
    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }
}
