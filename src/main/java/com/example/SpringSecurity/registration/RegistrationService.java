package com.example.SpringSecurity.registration;

import com.example.SpringSecurity.auth.ApplicationUser;
import com.example.SpringSecurity.auth.ApplicationUserService;
import com.example.SpringSecurity.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private  final ApplicationUserService applicationUserService;
    private final   EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email is not valid");
        }
        return applicationUserService.singUpUser(
                new ApplicationUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getIdentificationNo(),
                        request.getPhoneNumber(),
                        request.getLocation(),
                        ApplicationUserRole.STUDENT
                )

        );

    }

}
