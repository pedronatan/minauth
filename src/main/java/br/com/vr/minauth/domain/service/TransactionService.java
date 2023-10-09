package br.com.vr.minauth.domain.service;

import br.com.vr.minauth.api.request.TransactionRequest;
import br.com.vr.minauth.domain.entity.CardEntity;
import br.com.vr.minauth.domain.repository.CardRepository;
import br.com.vr.minauth.enumeration.CardStatus;
import br.com.vr.minauth.exception.TransactionNotAuthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private CardService cardService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardRepository cardRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String save(TransactionRequest transactionRequest) {
        cardRepository.findByNumber(transactionRequest.getNumeroCartao())
                .orElseThrow(() -> new TransactionNotAuthorizedException(CardStatus.CARTAO_INEXISTENTE));

        cardRepository.findByNumberAndPassword(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao())
                .orElseThrow(() -> new TransactionNotAuthorizedException(CardStatus.SENHA_INVALIDA));

        CardEntity cardEntity = cardRepository.findByNumberAndPasswordAndBalanceGreaterThanEqual(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao(), transactionRequest.getValor())
                .orElseThrow(() -> new TransactionNotAuthorizedException(CardStatus.SALDO_INSUFICIENTE));

        cardService.subtractAmountFromBalance(cardEntity, transactionRequest.getValor());

        return CardStatus.OK.toString();
    }


}
