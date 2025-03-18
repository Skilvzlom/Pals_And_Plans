package org.project.pals.service;

import jakarta.transaction.Transactional;
import org.project.pals.dto.response.UserResponseDto;
import org.project.pals.mapper.UserMapper;
import org.project.pals.model.user.User;
import org.project.pals.model.user.enums.RolesType;
import org.project.pals.repository.entity.RoleRepository;
import org.project.pals.repository.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void addNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.getRoleByRole(RolesType.ROLE_USER)));
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(user -> {
            userResponseDtos.add(userMapper.userToUserResponseDto(user));
        });
        return userResponseDtos;
    }

    @Override
    public UserResponseDto getUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) loadUserByUsername(username);
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User updatedUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        if (user.getUsername() != null) {
            updatedUser.setUsername(user.getUsername());
        }
        if (user.getRoles() != null) {
            updatedUser.setRoles(user.getRoles());
        }
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }
}
