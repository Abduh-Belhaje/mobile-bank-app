package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;


@Entity
@Table
@Getter
@Setter
public class Client implements UserDetails {

    @Id
    @GeneratedValue
    private Long c_Id;
    private String c_firstname;
    private String c_lastname;
    @Column(nullable = false , unique = true )
    private String c_email;
    @Column(nullable = false)
    private String c_password;

    private String c_phone;

    @Enumerated(EnumType.STRING)
    private Role role;


    public Client(){

    }
    public Client(String c_firstname, String c_lastname, String c_email, String c_password, String c_phone, Role role) {

        this.c_firstname = c_firstname;
        this.c_lastname = c_lastname;
        this.c_email = c_email;
        this.c_password = c_password;
        this.c_phone = c_phone;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return c_password;
    }

    @Override
    public String getUsername() {
        return c_email;
    }

    public String getFullName() {
        return c_firstname + ' ' + c_lastname;
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
}
