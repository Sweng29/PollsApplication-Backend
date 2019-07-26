package com.mantis.tech.polls.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mantis.tech.polls.models.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private Long userId;
    private String name;
    private String userName;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String emailAddress;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public UserPrincipal() {
    }

    public UserPrincipal(Long userId, String name, String userName, String password, String emailAddress, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.grantedAuthorities = grantedAuthorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> grantedAuthorityList = user.getUserRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        return new UserPrincipal(
                user.getUserId(),
                user.getName(),
                user.getUserName(),
                user.getPassword(),
                user.getEmailAddress(),
                grantedAuthorityList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
