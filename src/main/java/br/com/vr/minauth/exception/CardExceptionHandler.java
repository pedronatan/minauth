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

    @ExceptionHandler(value = DuplicateCardInsertionException.class)
    public ResponseEntity<CardResponse> handleDuplicateCardInsertionException(DuplicateCardInsertionException duplicateCardInsertionException){
        return new ResponseEntity<CardResponse>(duplicateCardInsertionException.getCardResponse(),HttpStatus.UNPROCESSABLE_ENTITY);
    }
}