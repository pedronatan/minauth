package br.com.vr.minauth.domain.service;

import br.com.vr.minauth.api.request.CardRequest;
import br.com.vr.minauth.api.response.CardResponse;
import br.com.vr.minauth.domain.entity.CardEntity;
import br.com.vr.minauth.domain.repository.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CardService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardRepository cardRepository;

    public CardResponse save(CardRequest cardRequest) {
        CardEntity cardEntity = modelMapper.map(cardRequest, CardEntity.class);
        cardEntity.setBalance(new BigDecimal(500));
        CardEntity cardEntitySaved = cardRepository.save(cardEntity);

        return modelMapper.map(cardEntitySaved, CardResponse.class);
    }

}
