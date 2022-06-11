package com.example.SpringSecurity.entityTables;

import com.example.SpringSecurity.auth.ApplicationUserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ApplicationUserPaymentsService {

    private final ApplicationUserPaymentsRepository applicationUserPaymentsRepository;
    private final ApplicationUserDao applicationUserDao;
    private final ApplicationUserProductRepository applicationUserProductRepository;




    public void makePayments(Long id,
                             ApplicationUserPayments applicationUserPayments,
                             Double total,Double amount
    ) {

        applicationUserDao.findById(id)
                .map(User -> {
                    applicationUserPayments.setApplicationUser(User);
                    ApplicationUserPayments applicationUserPayments1 = new ApplicationUserPayments(
                            applicationUserPayments.getAmount(),
                            applicationUserPayments.getModeOfPayment(),
                            LocalDateTime.now(),
                            User
                    );

                    return applicationUserPaymentsRepository.save(applicationUserPayments1);
                })
                .orElseThrow(() -> new IllegalStateException("customer with " + id + "does not exist"));
    }

//    public List<CurrentBalanceRequest> getTotalAmountPaidOfUserTotal(ApplicationUser applicationUser) {
//        return applicationUserPaymentsRepository.findTotalAmountPaidOfUser(applicationUser);
//    }
}

