package org.project.pals.model;


import jakarta.persistence.*;
import lombok.Data;
import org.project.pals.model.enums.RolesType;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RolesType role;

    public Role() {}

    public Role(RolesType role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role1)) return false;
        return id == role1.id && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
