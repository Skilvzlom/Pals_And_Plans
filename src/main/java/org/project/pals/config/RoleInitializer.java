package org.project.pals.config;

import org.project.pals.model.Role;
import org.project.pals.model.enums.RolesType;
import org.project.pals.repository.RoleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class RoleInitializer {

    @Bean
    @Transactional
    public ApplicationRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            for (RolesType role: RolesType.values()) {
                roleRepository.findById(role.ordinal() + 1)
                        .orElseGet(() -> roleRepository.save(new Role(role)));
            }
        };
    }
}
