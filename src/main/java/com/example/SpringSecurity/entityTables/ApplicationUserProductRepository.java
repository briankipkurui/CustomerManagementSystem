package com.example.SpringSecurity.entityTables;

import com.example.SpringSecurity.auth.ApplicationUser;
import com.example.SpringSecurity.requestClasses.ItemsCreditedToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationUserProductRepository extends JpaRepository<ApplicationUserProducts,Long> {



 @Query("SELECT SUM(c.total) from  ApplicationUserProducts  c   where c.applicationUser=?1 ")
 Double  getTotalProductsOfUser(ApplicationUser applicationUser);
 @Query("select new com.example.SpringSecurity.requestClasses.ItemsCreditedToUser" +
         "(c.quantity,c.description,c.unitPrice,c.total)" +
         " FROM ApplicationUserProducts c where c.applicationUser = ?1")
 List<ItemsCreditedToUser> getTotalItemsCreditedByUser(ApplicationUser applicationUser);

}
