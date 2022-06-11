package com.example.SpringSecurity.entityTables;

import com.example.SpringSecurity.auth.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "ApplicationUserProducts")
@Table(
        name = "application_user_products"
)
public class ApplicationUserProducts {
    @SequenceGenerator(
            name = "application_user_products_sequence",
            sequenceName = "application_user_products_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_user_products_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private  Long id;
    @Column(
            name = "quantity"
    )
    private Double quantity;
    private String description;
    @Column(
            name = "unit_price"
    )
    private  Double unitPrice;
    private Double total;

    @JsonBackReference
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


    public ApplicationUserProducts(Double quantity,
                                   String description,
                                   Double unitPrice,
                                   Double total,
                                   ApplicationUser applicationUser) {
        this.quantity = quantity;
        this.description = description;
        this.unitPrice = unitPrice;
        this.total = total;
        this.applicationUser = applicationUser;
    }
}
