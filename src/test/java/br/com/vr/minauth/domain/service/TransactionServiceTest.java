package br.com.vr.minauth.domain.service;

import br.com.vr.minauth.api.request.TransactionRequest;
import br.com.vr.minauth.domain.entity.CardEntity;
import br.com.vr.minauth.domain.repository.CardRepository;
import br.com.vr.minauth.enumeration.CardStatus;
import br.com.vr.minauth.exception.CardNotFoundException;
import br.com.vr.minauth.exception.TransactionNotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    CardRepository cardRepository;

    @Mock
    CardService cardService;

    CardEntity cardEntity;

    TransactionRequest transactionRequest;

    @BeforeEach
    void setUp() {
        cardEntity = CardEntity.builder()
                .number("1234567890")
                .password("abcdefghij")
                .balance(BigDecimal.valueOf(10.00))
                .build();

        transactionRequest = new TransactionRequest();
        transactionRequest.setNumeroCartao("1234567890");
        transactionRequest.setSenhaCartao("abcdefghij");
    }

    @Test
    void saveTransactionCardNotFoundFailure() {
        when(cardRepository.findByNumber(transactionRequest.getNumeroCartao()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(TransactionNotAuthorizedException.class,
                () -> transactionService.save(transactionRequest));
    }

    @Test
    void saveTransactionPasswordFailure() {
        when(cardRepository.findByNumber(cardEntity.getNumber()))
                .thenReturn(Optional.ofNullable(cardEntity));
        when(cardRepository.findByNumberAndPassword(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(TransactionNotAuthorizedException.class,
                () -> transactionService.save(transactionRequest));
    }

    @Test
    void saveTransactionBalanceInsufficientFailure() {
        when(cardRepository.findByNumber(cardEntity.getNumber()))
                .thenReturn(Optional.ofNullable(cardEntity));
        when(cardRepository.findByNumberAndPassword(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao()))
                .thenReturn(Optional.ofNullable(cardEntity));
        when(cardRepository.findByNumberAndPasswordAndBalanceGreaterThanEqual(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao(), transactionRequest.getValor()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(TransactionNotAuthorizedException.class,
                () -> transactionService.save(transactionRequest));
    }

    @Test
    void saveTransactionSuccess() {
        when(cardRepository.findByNumber(cardEntity.getNumber()))
                .thenReturn(Optional.ofNullable(cardEntity));
        when(cardRepository.findByNumberAndPassword(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao()))
                .thenReturn(Optional.ofNullable(cardEntity));
        when(cardRepository.findByNumberAndPasswordAndBalanceGreaterThanEqual(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao(), transactionRequest.getValor()))
                .thenReturn(Optional.ofNullable(cardEntity));

        String result = transactionService.save(transactionRequest);
        assertEquals(CardStatus.OK.toString(), result);
    }
}