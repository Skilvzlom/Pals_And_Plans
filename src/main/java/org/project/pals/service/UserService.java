package org.project.pals.service;

import org.project.pals.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    void addNewUser(User user);
}
