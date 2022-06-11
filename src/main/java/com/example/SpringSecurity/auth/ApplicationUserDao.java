package com.example.SpringSecurity.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ApplicationUserDao extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE ApplicationUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableApplicationUser(String email);

   @Transactional(readOnly = true)
    @Query("SELECT s From ApplicationUser" +
            " s WHERE s.phoneNumber =?1")
    Optional<ApplicationUser> findByPhoneNumber(String phoneNumber);

    Optional<ApplicationUser> findByIdentificationNo(String identificationNo);

}
