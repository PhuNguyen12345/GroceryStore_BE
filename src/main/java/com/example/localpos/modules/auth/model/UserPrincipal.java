package com.example.localpos.modules.auth.model;

import com.example.localpos.enums.EmployeeRole;
import com.example.localpos.modules.hr.entity.Employee;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String fullName;
    private final String email;
    private final EmployeeRole role;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal of(Employee employee) {
        return new UserPrincipal(
                employee.getId(),
                employee.getUsername(),
                employee.getPasswordHash(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getRole(),
                Boolean.TRUE.equals(employee.getIsActive()),
                List.of(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name()))
        );
    }

    private UserPrincipal(Long id, String username, String password,
                          String fullName, String email, EmployeeRole role,
                          boolean active,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.active = active;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
