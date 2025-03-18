package org.project.pals.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegistrationRequestDto", description = "DTO объект для регистрации нового пользователя")
public record RegistrationRequestDto(
        @Schema(description = "Имя пользователя")
        String username,
        @Schema(description = "email пользователя")
        String email,
        @Schema(description = "Пароль пользователя")
        String password,
        @Schema(description = "Реальное имя пользователя")
        String name) {
}
