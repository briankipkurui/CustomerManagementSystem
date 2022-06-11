package com.example.SpringSecurity.entityTables;
import com.example.SpringSecurity.auth.ApplicationUser;
import com.example.SpringSecurity.requestClasses.TotalAmountPaidByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ApplicationUserPaymentsRepository extends JpaRepository<ApplicationUserPayments, Long> {

    @Query("SELECT SUM(c.amount) from  ApplicationUserPayments  c  where  c.applicationUser =?1")
    Double getTotalAmountPaidByUser(ApplicationUser applicationUser);

    @Query("select new com.example.SpringSecurity.requestClasses.TotalAmountPaidByUser" +
            "(c.amount, c.paidAt) FROM ApplicationUserPayments c where c.applicationUser = ?1")
     List<TotalAmountPaidByUser> getTotalAmountPaidOfUser(ApplicationUser applicationUser);


}
