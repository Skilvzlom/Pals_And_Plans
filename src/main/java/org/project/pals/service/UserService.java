package org.project.pals.service;

import org.project.pals.dto.response.UserResponseDto;
import org.project.pals.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UserService extends UserDetailsService {
    void addNewUser(User user);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserByUsername(String username) throws UsernameNotFoundException;
    void updateUser(User user);
    void deleteUser(String username);
}
