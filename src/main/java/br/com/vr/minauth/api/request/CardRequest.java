package br.com.vr.minauth.api.request;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardRequest {

    @NotBlank
    private String numeroCartao;

    @NotBlank
    private String senha;
}
