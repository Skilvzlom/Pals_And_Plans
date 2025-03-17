package org.project.pals.service;

import org.project.pals.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    void addNewUser(User user);
}
