package org.project.pals.mapper;

import org.mapstruct.Mapper;
import org.project.pals.dto.request.RegistrationRequestDto;
import org.project.pals.dto.response.UserResponseDto;
import org.project.pals.model.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto userToUserResponseDto(User user);
    User registrationRequestDtoToUser(RegistrationRequestDto registrationRequestDto);
}
