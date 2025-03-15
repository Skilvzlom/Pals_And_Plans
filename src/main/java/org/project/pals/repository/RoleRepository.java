package org.project.pals.repository;

import org.project.pals.model.Role;
import org.project.pals.model.enums.RolesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getRoleByRole(RolesType role);
}
