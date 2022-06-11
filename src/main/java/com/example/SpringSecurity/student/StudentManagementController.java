package com.example.SpringSecurity.student;

import com.example.SpringSecurity.auth.ApplicationUser;
import com.example.SpringSecurity.auth.ApplicationUserService;
import com.example.SpringSecurity.entityTables.ApplicationUserProducts;
import com.example.SpringSecurity.entityTables.ApplicationUserProductsService;
import com.example.SpringSecurity.registration.RegistrationRequest;
import com.example.SpringSecurity.registration.RegistrationService;
import com.example.SpringSecurity.registration.token.ConfirmationToken;
import com.example.SpringSecurity.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("adminPane/management/api/v1/customer")
public class StudentManagementController {
    private  final ApplicationUserService applicationUserService;
    private  final RegistrationService registrationService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ApplicationUserProductsService applicationUserProductsService;

    @GetMapping(path = "all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<ApplicationUser> getAllStudents(){
     return applicationUserService.getAllStudents();
 }

   @PostMapping(path = "registerUser")
   @PreAuthorize("hasAuthority('customer:write')")
    public String register(@Valid  @RequestBody RegistrationRequest request){
       return registrationService.register(request);
    }

    @PostMapping(path = "registerAdmin")
    @PreAuthorize("hasAuthority('customer:write')")
    public String registerAdmin(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.registerAdmin(request);
    }

    @PostMapping(path = "registerAdminTrainee")
    @PreAuthorize("hasAuthority('customer:write')")
    public String registerAdminTrainee(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.registerAdminTrainee(request);
    }
   @DeleteMapping(path = "{id}")
   @PreAuthorize("hasAuthority('customer:write')")
    public void deleteCustomer(@PathVariable("id") Long id){
        applicationUserService.deleteCustomer(id);
    }
    @GetMapping(path = "allConfirmation")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<ConfirmationToken> getAllConfirmation(){
        return confirmationTokenService.getAllConfirmation();
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
      public ApplicationUser getApplicationUserById(@PathVariable("id")  Long id){
        return  applicationUserService.getApplicationUserById(id);
     }
    @PostMapping(path = "product/{id}")
    @PreAuthorize("hasAuthority('customer:write')")
    public void addProduct(@PathVariable("id") Long  id ,
                           @RequestBody ApplicationUserProducts applicationUserProducts){
      applicationUserProductsService.addProduct(id,applicationUserProducts);
    }
    @GetMapping(path = "allProducts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<ApplicationUserProducts> getAllProducts(){
        return applicationUserProductsService.getAllProducts();
    }
    @GetMapping(path = "joinInformation/{applicationUser}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public Double getJoinInformation(@PathVariable("applicationUser") ApplicationUser applicationUser){
        return applicationUserProductsService.getJoinInformation(applicationUser);
    }

}
