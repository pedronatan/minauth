package br.com.vr.minauth.exception;

import br.com.vr.minauth.enumeration.CardStatus;
import lombok.Getter;

@Getter
public class TransactionNotAuthorizedException extends RuntimeException{
    private String cardStatus;

    public TransactionNotAuthorizedException(CardStatus cardStatus) {
       this.cardStatus = cardStatus.toString();
    }
}
