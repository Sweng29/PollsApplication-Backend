package com.mantis.tech.polls.models;

import com.mantis.tech.polls.models.audit.AuditDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/*@AllArgsConstructor
@NoArgsConstructor*/
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
    @Size(min = 6, message = "Password should be of minimum 6 characters.")
    @NotBlank(message = "Password should not be empty.")
    @NotNull(message = "Password should not be null.")
    private String password;
    @NotBlank(message = "Email Address should not be empty.")
    @NotNull(message = "Email Address should not be null.")
    @Column(nullable = false, unique = true)
    private String emailAddress;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> userRoles = new HashSet<Role>();

    public User(@NotNull(message = "Name should not be null.") @NotBlank(message = "Name should not be empty.") String name, String userName, @Size(min = 6, message = "Password should be of minimum 6 characters.") @NotBlank(message = "Password should not be empty.") @NotNull(message = "Password should not be null.") String password, @NotBlank(message = "Email Address should not be empty.") @NotNull(message = "Email Address should not be null.") String emailAddress) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public User(@NotNull(message = "Name should not be null.") @NotBlank(message = "Name should not be empty.") String name, String userName, @Size(min = 6, message = "Password should be of minimum 6 characters.") @NotBlank(message = "Password should not be empty.") @NotNull(message = "Password should not be null.") String password, @NotBlank(message = "Email Address should not be empty.") @NotNull(message = "Email Address should not be null.") String emailAddress, Set<Role> userRoles) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.userRoles = userRoles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }
}
