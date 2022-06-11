package com.example.SpringSecurity.registration;


import com.example.SpringSecurity.auth.ApplicationUser;

import com.example.SpringSecurity.auth.ApplicationUserService;
import com.example.SpringSecurity.entityTables.*;
import com.example.SpringSecurity.requestClasses.ItemsCreditedToUser;
import com.example.SpringSecurity.requestClasses.TotalAmountPaidByUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.security.core.Authentication;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    private final ApplicationUserService applicationUserService;
    private final ApplicationUserProductsService applicationUserProductsService;
    private final ApplicationUserProductRepository applicationUserProductRepository;
    private final ApplicationUserPaymentsService applicationUserPaymentsService;
    private final ApplicationUserPaymentsRepository applicationUserPaymentsRepository;


    @PostMapping(path = "registerUser")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
    @PostMapping(path = "registerAdmin")
    public String registerAdmin(@RequestBody RegistrationRequest request) {
        return registrationService.registerAdmin(request);
    }
    @PostMapping(path = "registerAdminTrainee")
    public String registerAdminTrainee(@RequestBody RegistrationRequest request) {
        return registrationService.registerAdminTrainee(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);

    }
    @GetMapping(path = "confirmAdmin")
    public String confirmAdmin(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);

    }
    @GetMapping(path = "confirmAdminTrainee")
    public String confirmAdminTrainee(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);

    }
    @GetMapping(path = "{id}")
    public ApplicationUser getApplicationUserById(@PathVariable("id")  Long id){
        return  applicationUserService.getApplicationUserById(id);
    }

    @PostMapping(path = "product/{id}")
    public void addProduct(@PathVariable("id") Long  id ,
                           @RequestBody ApplicationUserProducts applicationUserProducts){
        applicationUserProductsService.addProduct(id,applicationUserProducts);
    }


//
//    @GetMapping(path = "joinInformation/{applicationUser}")
//    public Double getJoinInformation(@PathVariable("applicationUser") ApplicationUser applicationUser){
//        return applicationUserProductsService.getJoinInformation(applicationUser);
//    }

    @GetMapping("amountPaidByUser/{applicationUser}")
    public List<TotalAmountPaidByUser> getTotalAmountPaidOfUser(@PathVariable("applicationUser") ApplicationUser applicationUser){
        return applicationUserPaymentsRepository.getTotalAmountPaidOfUser(applicationUser);
    }
    @GetMapping("itemsTakenByUser/{applicationUser}")
    public List<ItemsCreditedToUser> getTotalItemsCreditedByUser(@PathVariable("applicationUser") ApplicationUser applicationUser){
        return applicationUserProductRepository.getTotalItemsCreditedByUser(applicationUser);
    }

    @PostMapping(path = "makePayments/{id}")
    public void makePayments(@PathVariable("id") Long id,
                             @RequestBody ApplicationUserPayments applicationUserPayments,
                             Double total,Double amount
                                                 ) {
        applicationUserPaymentsService.makePayments(id,applicationUserPayments,total,amount);
    }

    @GetMapping(path = "refresh")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response,
                             Authentication authResult) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        String authorizationHeader = request.getHeader("Authorization");

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        String refresh_token = authorizationHeader.replace("Bearer ", "");
        try {

            String secreteKey = "SecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecure";
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secreteKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(refresh_token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            Optional<ApplicationUser> user = applicationUserService.getUsername(username);
            String key = "SecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecureSecure";
            String access_token = Jwts.builder()
//                    .setSubject(authResult.getName())
                    .setSubject(user.get().getUsername())
                    .claim("authorities", authResult.getAuthorities())
                    .setIssuedAt(new Date())
//                    .setIssuedAt(new Date())
                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
//                    .setExpiration(new java.sql.Date(System.currentTimeMillis()+30*60*1000))
                    .setIssuer(request.getRequestURL().toString())
                    .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                    .compact();
            response.addHeader("access_token","Bearer"+access_token);
            response.addHeader("refresh_token","Bearer"+ refresh_token);
            Map<String, String> tokens = new HashMap<>();
            tokens.put( "access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//            response.addHeader("Authorization","Bearer"+access_token);
//            response.addHeader("Authorization","Bearer"+refresh_token);
        } catch (JwtException exception) {
            response.setHeader("error", exception.getMessage());
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", exception.getMessage());
            response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
        throw new RuntimeException("Refresh refresh_token expired");

    }

}
