package com.example.SpringSecurity.auth;

import com.example.SpringSecurity.exeception.CustomerNotFoundException;
import com.example.SpringSecurity.registration.token.ConfirmationToken;
import com.example.SpringSecurity.registration.token.ConfirmationTokenService;
import com.example.SpringSecurity.security.PasswordConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ApplicationUserService  implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final ApplicationUserDao applicationUserDao;
    private  final PasswordConfig passwordConfig;
    private final ConfirmationTokenService confirmationTokenService;


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
        boolean phoneNumberExist = applicationUserDao
                .findByPhoneNumber(applicationUser.getPhoneNumber())
                .isPresent();
        if (phoneNumberExist){
            throw new IllegalStateException("phone number already exist");
        }
        boolean identificationNumberExist = applicationUserDao
                .findByIdentificationNo(applicationUser.getIdentificationNo())
                .isPresent();

        if(identificationNumberExist){
            throw new IllegalStateException("identificationNumber already exist");
        }

        String encodePassword = passwordConfig.bCryptPasswordEncoder()
                .encode(applicationUser.getPassword());

        applicationUser.setPassword(encodePassword);



        applicationUserDao.save(applicationUser);
        String token= UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
               token,
               LocalDateTime.now(),
               LocalDateTime.now().plusMinutes(15),
               applicationUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        //  TODO SEND EMAIL
        return token;
    }
    public int enableApplicationUser(String email) {
        return applicationUserDao.enableApplicationUser(email);
    }
    public Optional<ApplicationUser> getUsername(String email){
        return applicationUserDao.findByEmail(email);
    }


    public List<ApplicationUser> getAllStudents() {
        return applicationUserDao.findAll();
    }

    public void deleteCustomer(Long id) {
        if(!applicationUserDao.existsById(id)){
          throw new CustomerNotFoundException(
            "customer with"+id+"does not exist"
          );
        }
     applicationUserDao.deleteById(id);

    }
    public Optional<ApplicationUser> getPhoneNumber(String phoneNumber){
        return applicationUserDao.findByPhoneNumber(phoneNumber);
    }


    public ApplicationUser getApplicationUserById(Long id) {
        return getAllStudents()
                .stream()
                .filter(ApplicationUser -> ApplicationUser.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("customer with "+id+ " was not found"));
    }
}
