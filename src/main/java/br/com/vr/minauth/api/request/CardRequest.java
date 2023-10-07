package br.com.vr.minauth.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

    @NotBlank
    private String numeroCartao;

    @NotBlank
    private String senha;
}
