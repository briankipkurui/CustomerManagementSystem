package com.example.SpringSecurity.auth;

import com.example.SpringSecurity.entityTables.ApplicationUserPayments;
import com.example.SpringSecurity.entityTables.ApplicationUserProducts;
import com.example.SpringSecurity.registration.token.ConfirmationToken;
import com.example.SpringSecurity.security.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

import static javax.swing.UIManager.get;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ApplicationUser")
@Table(
        name = "application_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "application_user_email_unique",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "application_user_phone_number_unique",
                        columnNames = "phone_number"
                )
        }
)
public class ApplicationUser implements UserDetails {


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
    @Column(
            name = "id",
            updatable = false
    )
    private  Long id;
    @NotBlank(message = "it cannot be empty")
    @Column(
            name = "first_name",
            nullable = false
    )
    private  String  firstName;
    @NotBlank(message = "it cannot be empty")
    @Column(
            name = "last_name",
            nullable = false
    )
    private  String  lastName;
    @NotBlank(message = "it cannot be empty")
    @Email(message = "Email should be valid")
    @Column(
            name = "email",
            nullable = false
    )
    private  String  email;
    @NotBlank(message = "it cannot be empty")
    @Column(
            name = "password"
    )
    private  String  password;
    @Size(min = 8,max = 8,message = "give minimum of 8 characters and maximum of 8 characters")
    @NotBlank(message = "it cannot be empty")
    @Pattern(regexp = "^[0-9]+$" ,message = "value must be of digits only")
    @Column(
            name = "identification_no",
            nullable = false,
            columnDefinition = "VARCHAR",
            length = 8
    )
    private  String  identificationNo;
    @Size(min = 10,max = 10,message = "give minimum of 10 characters and maximum of 10 characters")
    @NotBlank(message = "it cannot be empty")
    @Pattern(regexp = "^[1-9]|[0-9]{2,}$",message = "value must be of digits only")
    @Column(
            name = "phone_number",
            nullable = false,
            columnDefinition = "VARCHAR",
            length = 10
    )
    private  String  phoneNumber;

    @NotBlank(message = "it cannot be empty")
    @Column(
            name = "location",
            nullable = false
    )
    private  String  location;
    @Enumerated(EnumType.STRING)
    ApplicationUserRole applicationUserRole;

    private Boolean locked = false;
    private  Boolean enabled = false;
    @JsonIgnore
    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "applicationUser",
            orphanRemoval = true
    )
    private ConfirmationToken confirmationToken;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(
            mappedBy = "applicationUser",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ApplicationUserProducts> applicationUserProducts = new ArrayList();

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "applicationUser",
            orphanRemoval = true
    )
    private List<ApplicationUserPayments>  applicationUserPayment = new ArrayList();

    public ApplicationUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String identificationNo,
                           String phoneNumber,
                           String location,
                           ApplicationUserRole applicationUserRole
                           )

    {
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

    public ConfirmationToken getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(ConfirmationToken confirmationToken) {
        this.confirmationToken = confirmationToken;
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

//    public String getUsername(String subject) {
//        return email;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
