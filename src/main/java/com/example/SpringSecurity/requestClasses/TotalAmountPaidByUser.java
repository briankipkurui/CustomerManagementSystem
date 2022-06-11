package com.example.SpringSecurity.requestClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TotalAmountPaidByUser {
    private  Double amount;
    private LocalDateTime paidAt;

    public TotalAmountPaidByUser(Double amount, LocalDateTime paidAt) {
        this.amount = amount;
        this.paidAt = paidAt;
    }
}
