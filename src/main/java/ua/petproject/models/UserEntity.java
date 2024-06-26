package ua.petproject.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.petproject.models.enums.Roles;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public boolean hasRole(String roleName) {
        return roles != null && roles.stream()
                .anyMatch(role -> role.name().equals(roleName));
    }
}
