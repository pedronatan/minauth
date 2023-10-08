package br.com.vr.minauth.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank
    private String numeroCartao;

    @NotBlank
    private String senhaCartao;

    @NotNull
    private BigDecimal valor;

}