package com.example.SpringSecurity.requestClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ItemsCreditedToUser {
   private Double quantity;
   private String description;
   private Double unitPrice;
   private Double total;

    public ItemsCreditedToUser(Double quantity, String description, Double unitPrice, Double total) {
        this.quantity = quantity;
        this.description = description;
        this.unitPrice = unitPrice;
        this.total = total;
    }
}
