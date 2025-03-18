package org.project.pals.model.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.project.pals.model.user.enums.RolesType;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Setter
@Getter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolesType role;

    public Role() {}

    public Role(RolesType role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }
}
