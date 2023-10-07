package br.com.vr.minauth.exception;

import br.com.vr.minauth.api.response.CardResponse;
import lombok.Getter;

@Getter
public class DuplicateCardInsertionException extends RuntimeException{
    private CardResponse cardResponse;

    public DuplicateCardInsertionException(CardResponse cardResponse) {
        this.cardResponse = cardResponse;
    }
}
