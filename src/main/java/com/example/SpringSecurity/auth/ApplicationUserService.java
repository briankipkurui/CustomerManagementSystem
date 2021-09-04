package com.example.SpringSecurity.auth;

import com.example.SpringSecurity.security.PasswordConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationUserService  implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final ApplicationUserDao applicationUserDao;
    private  final PasswordConfig passwordConfig;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return applicationUserDao.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String singUpUser(ApplicationUser applicationUser) {
        //check if user exist
        boolean userExist = applicationUserDao
                .findByEmail(applicationUser.getEmail())
                .isPresent();
        if (userExist) {
            throw new IllegalStateException("email already taken");
        }
        String encodePassword = passwordConfig.bCryptPasswordEncoder()
                .encode(applicationUser.getPassword());

        applicationUser.setPassword(encodePassword);



        applicationUserDao.save(applicationUser);
        return "it works";
    }

}
