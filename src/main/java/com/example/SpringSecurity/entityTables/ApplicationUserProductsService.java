package com.example.SpringSecurity.entityTables;

import com.example.SpringSecurity.auth.ApplicationUser;
import com.example.SpringSecurity.auth.ApplicationUserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationUserProductsService {
    private final ApplicationUserProductRepository applicationUserProductRepository;
    private final ApplicationUserDao applicationUserDao;
    private final ApplicationUserPaymentsRepository applicationUserPaymentsRepository;


    public List<ApplicationUserProducts> getAllProducts() {
        return applicationUserProductRepository.findAll();
    }


    public void addProduct(Long id, ApplicationUserProducts applicationUserProducts) {

        applicationUserDao.findById(id)
                .map(account -> {
                    applicationUserProducts.setApplicationUser(account);
                    Double Total =
                            Double.valueOf((applicationUserProducts.getQuantity() *
                                    applicationUserProducts.getUnitPrice()));
                    ApplicationUserProducts applicationUserProducts1 = new ApplicationUserProducts(
                            applicationUserProducts.getQuantity(),
                            applicationUserProducts.getDescription(),
                            applicationUserProducts.getUnitPrice(),
                            Total,
                            account
                    );
                    return applicationUserProductRepository.save(applicationUserProducts1);
                }).orElseThrow(() -> new IllegalStateException("customer with " + id + "does not exist"));
    }


    public Double getJoinInformation(ApplicationUser applicationUser) {

        Double joinInformation = applicationUserProductRepository.getTotalProductsOfUser(applicationUser)
                                    - applicationUserPaymentsRepository.getTotalAmountPaidByUser(applicationUser);
        return joinInformation;
    }
}