package br.com.vr.minauth.exception;

import br.com.vr.minauth.api.response.CardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CardExceptionHandler {

    @ExceptionHandler(value = DuplicateCardNumberInsertionException.class)
    public ResponseEntity<CardResponse> handleDuplicateCardInsertionException(DuplicateCardNumberInsertionException duplicateCardNumberInsertionException){
        return new ResponseEntity<CardResponse>(duplicateCardNumberInsertionException.getCardResponse(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = CardNotFoundException.class)
    public ResponseEntity handleCardNotFoundException(){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}