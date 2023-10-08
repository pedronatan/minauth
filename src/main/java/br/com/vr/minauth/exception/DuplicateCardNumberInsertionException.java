package br.com.vr.minauth.exception;

import br.com.vr.minauth.api.response.CardResponse;
import lombok.Getter;

@Getter
public class DuplicateCardNumberInsertionException extends RuntimeException{
    private CardResponse cardResponse;

    public DuplicateCardNumberInsertionException(CardResponse cardResponse) {
        this.cardResponse = cardResponse;
    }
}
