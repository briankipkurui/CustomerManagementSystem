package com.example.SpringSecurity.entityTables;

import com.example.SpringSecurity.auth.ApplicationUser;
import com.example.SpringSecurity.requestClasses.TotalAmountPaidByUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "ApplicationUserPayments")
@Table(name = "application_user_payments")
public class ApplicationUserPayments {


    @SequenceGenerator(
            name = "application_user_payments_sequence",
            sequenceName = "application_user_payments_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "application_user_payments_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    private Double amount;

    @Enumerated(EnumType.STRING)
    ModeOfPayment modeOfPayment;
    private LocalDateTime paidAt;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "application_user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "application_user_fk"
            )
    )
    private ApplicationUser applicationUser;

    public ApplicationUserPayments(Double amount,
                                   ModeOfPayment modeOfPayment,
                                   LocalDateTime paidAt,
                                   ApplicationUser applicationUser) {
        this.amount = amount;
        this.modeOfPayment = modeOfPayment;
        this.paidAt = paidAt;
        this.applicationUser = applicationUser;
    }
}
