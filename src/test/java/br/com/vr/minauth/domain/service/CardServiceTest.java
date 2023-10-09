package br.com.vr.minauth.domain.service;

import br.com.vr.minauth.api.request.CardRequest;
import br.com.vr.minauth.api.response.CardResponse;
import br.com.vr.minauth.domain.entity.CardEntity;
import br.com.vr.minauth.domain.repository.CardRepository;
import br.com.vr.minauth.exception.CardNotFoundException;
import br.com.vr.minauth.exception.DuplicateCardNumberInsertionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @InjectMocks
    CardService cardService;

    @Mock
    CardRepository cardRepository;

    @Mock
    ModelMapper modelMapper;

    CardEntity cardEntity;

    @BeforeEach
    void setUp() {
        cardEntity = CardEntity.builder()
                .number("1234567890")
                .password("abcdefghij")
                .balance(BigDecimal.valueOf(10.00))
                .build();
    }

    @Test
    void saveCardNotDuplicatedSuccess() {
        CardRequest cardRequest = CardRequest.builder()
                .numeroCartao(cardEntity.getNumber())
                .senha(cardEntity.getPassword())
                .build();
        CardResponse cardResponse = CardResponse.builder()
                .numeroCartao(cardEntity.getNumber())
                .senha(cardEntity.getPassword())
                .build();

        when(modelMapper.map(cardRequest, CardEntity.class))
                .thenReturn(cardEntity);
        when(cardRepository.findByNumber(cardEntity.getNumber()))
                .thenReturn(Optional.ofNullable(null));
        when(cardRepository.save(cardEntity))
                .thenReturn(cardEntity);
        when(modelMapper.map(cardEntity, CardResponse.class))
                .thenReturn(cardResponse);

        CardResponse cardResponseSaved = cardService.save(cardRequest);

        assertEquals(cardResponseSaved, cardResponse);
        assertEquals(BigDecimal.valueOf(500), cardEntity.getBalance());
        assertEquals(cardResponseSaved.getNumeroCartao(), cardRequest.getNumeroCartao());
    }

    @Test
    void saveCardDuplicatedFailure() {
        CardRequest cardRequest = CardRequest.builder()
                .numeroCartao(cardEntity.getNumber())
                .senha(cardEntity.getPassword())
                .build();

        when(modelMapper.map(cardRequest, CardEntity.class))
                .thenReturn(cardEntity);
        when(cardRepository.findByNumber(cardEntity.getNumber()))
                .thenReturn(Optional.ofNullable(cardEntity));
        when(modelMapper.map(cardEntity, CardResponse.class))
                .thenReturn(null);

        assertThrows(DuplicateCardNumberInsertionException.class,
                () -> cardService.save(cardRequest));
    }

    @Test
    void findCardByNumberSuccess() {
        when(cardRepository.findByNumber(cardEntity.getNumber()))
                .thenReturn(Optional.ofNullable(cardEntity));

        Optional<CardEntity> optionalCardEntity = cardService.findByNumber(cardEntity.getNumber());

        assertEquals(cardEntity.getNumber(), optionalCardEntity.get().getNumber());
    }

    @Test
    void findCardByNumberAndPasswordSuccess() {
        when(cardRepository.findByNumberAndPassword(cardEntity.getNumber(), cardEntity.getPassword()))
                .thenReturn(Optional.ofNullable(cardEntity));

        Optional<CardEntity> optionalCardEntity = cardService.findByNumberAndPassword(cardEntity.getNumber(), cardEntity.getPassword());

        assertEquals(cardEntity.getNumber(), optionalCardEntity.get().getNumber());
    }

    @Test
    void findCardWithBalanceGreaterThanOrEqualSuccess() {
        when(cardRepository.findByNumberAndPasswordAndBalanceGreaterThanEqual(cardEntity.getNumber(), cardEntity.getPassword(), cardEntity.getBalance()))
                .thenReturn(Optional.ofNullable(cardEntity));

        Optional<CardEntity> optionalCardEntity = cardService.findByNumberAndPasswordAndBalanceGreaterThanEqual(cardEntity.getNumber(), cardEntity.getPassword(), cardEntity.getBalance());

        assertEquals(cardEntity.getNumber(), optionalCardEntity.get().getNumber());
    }

    @Test
    void findBalanceByNumberFailure() {
        when(cardRepository.findBalanceByNumber(cardEntity.getNumber()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(CardNotFoundException.class,
                () -> cardService.findBalanceByNumber(cardEntity.getNumber()));
    }

    @Test
    void subtractAmountFromBalance() {
        BigDecimal minuend = BigDecimal.valueOf(200);
        BigDecimal subtrahend = BigDecimal.valueOf(100);
        cardEntity.setBalance(minuend);
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);

        CardEntity cardEntitySaved = cardService.subtractAmountFromBalance(cardEntity, subtrahend);

        assertEquals(minuend, cardEntitySaved.getBalance().add(subtrahend));
    }
}