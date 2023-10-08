package br.com.vr.minauth.domain.service;

import br.com.vr.minauth.api.request.CardRequest;
import br.com.vr.minauth.api.response.CardResponse;
import br.com.vr.minauth.domain.entity.CardBalanceView;
import br.com.vr.minauth.domain.entity.CardEntity;
import br.com.vr.minauth.domain.repository.CardRepository;
import br.com.vr.minauth.exception.CardNotFoundException;
import br.com.vr.minauth.exception.DuplicateCardNumberInsertionException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CardService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardRepository cardRepository;

    private static final BigDecimal FIRST_BALANCE = new BigDecimal(500);

    public CardResponse save(CardRequest cardRequest) {
        CardEntity cardEntity = modelMapper.map(cardRequest, CardEntity.class);

        verifyDuplicatedNumber(cardEntity);

        cardEntity.setBalance(FIRST_BALANCE);
        CardEntity cardEntitySaved = cardRepository.save(cardEntity);

        return modelMapper.map(cardEntitySaved, CardResponse.class);
    }

    private void verifyDuplicatedNumber(CardEntity cardEntity) {
        this.findByNumber(cardEntity.getNumber())
                .ifPresent(card -> {
                    throw new DuplicateCardNumberInsertionException(modelMapper.map(cardEntity, CardResponse.class));
                });
    }

    public Optional<CardEntity> findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    public BigDecimal getBalanceByNumber(String cardNumber) {
        return cardRepository.findOneBalanceByNumber(cardNumber)
                .map(CardBalanceView::getBalance)
                .orElseThrow(CardNotFoundException::new);

    }
}