package com.example.SpringSecurity.auth;

import com.example.SpringSecurity.security.ApplicationUserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

import static javax.swing.UIManager.get;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class ApplicationUser  implements UserDetails {
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private  Long id;
    private  String  firstName;
    private  String  lastName;
    private  String  email;
    private  String  password;
    private  String  identificationNo;
    private  String  phoneNumber;
    private  String  location;
    @Enumerated(EnumType.STRING)
    ApplicationUserRole applicationUserRole;
    private  boolean isAccountNonExpired = true;
    private  boolean isAccountNonLocked = true;
    private  boolean isCredentialsNonExpired = true;
    private  boolean isEnabled = true;

    public ApplicationUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String identificationNo,
                           String phoneNumber,
                           String location,
                           ApplicationUserRole applicationUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.identificationNo = identificationNo;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.applicationUserRole = applicationUserRole;
    }



    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> authorities = applicationUserRole.getGrantedAuthorities();
 return  authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
