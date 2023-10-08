package br.com.vr.minauth.domain.service;

import br.com.vr.minauth.api.request.TransactionRequest;
import br.com.vr.minauth.domain.entity.CardEntity;
import br.com.vr.minauth.enumeration.CardStatus;
import br.com.vr.minauth.exception.TransactionNotAuthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private CardService cardService;

    @Autowired
    private ModelMapper modelMapper;

    public String save(TransactionRequest transactionRequest) {
        cardService.findByNumber(transactionRequest.getNumeroCartao())
                .orElseThrow(() -> new TransactionNotAuthorizedException(CardStatus.CARTAO_INEXISTENTE));

        cardService.findByNumberAndPassword(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao())
                .orElseThrow(() -> new TransactionNotAuthorizedException(CardStatus.SENHA_INVALIDA));

        CardEntity cardEntity = cardService.findByNumberAndPasswordAndBalanceGreaterThanEqual(transactionRequest.getNumeroCartao(), transactionRequest.getSenhaCartao(), transactionRequest.getValor())
                .orElseThrow(() -> new TransactionNotAuthorizedException(CardStatus.SALDO_INSUFICIENTE));

        cardService.subtractAmountFromBalance(cardEntity, transactionRequest.getValor());

        return CardStatus.OK.toString();
    }


}
