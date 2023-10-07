package br.com.vr.minauth.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "cards")
@Entity(name = "Card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "number", unique = true)
    private String number;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;

}
