package br.com.vr.minauth.api.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponse {
    private String numeroCartao;

    private String senha;
}
