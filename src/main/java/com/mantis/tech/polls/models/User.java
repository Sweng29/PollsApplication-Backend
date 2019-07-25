package com.mantis.tech.polls.models;

import com.mantis.tech.polls.models.audit.AuditDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditDate {

    @Id
    @GeneratedValue
    private Long userId;
    @NotNull(message = "Name should not be null.")
    @NotBlank(message = "Name should not be empty.")
    private String name;
    @Column(nullable = false,unique = true)
    private String userName;
    @Size(min = 6,message = "Passwor should be of minimum 6 characters.")
    @NotBlank(message = "Password should not be empty.")
    @NotNull(message = "Password should not be null.")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> userRoles = new HashSet<Role>();

}
