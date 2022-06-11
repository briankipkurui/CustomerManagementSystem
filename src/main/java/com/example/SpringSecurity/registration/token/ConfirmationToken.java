package com.example.SpringSecurity.registration.token;

import com.example.SpringSecurity.auth.ApplicationUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "ConfirmationToken")
@Table(
        name = "confirmation_token"
)
public class ConfirmationToken {
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private  Long id;
    @Column(nullable = false)
    private  String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @OneToOne
    @JoinColumn(

            name = "application_user_id",
            foreignKey = @ForeignKey(
                    name = "application_user_fk"
            )
    )
    private ApplicationUser applicationUser;
    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             ApplicationUser applicationUser
                            ) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.applicationUser = applicationUser;
    }


}
