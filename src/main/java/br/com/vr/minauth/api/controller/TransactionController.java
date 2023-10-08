package br.com.vr.minauth.api.controller;

import br.com.vr.minauth.api.request.TransactionRequest;
import br.com.vr.minauth.api.response.CardResponse;
import br.com.vr.minauth.domain.service.TransactionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacoes")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "201"), @ApiResponse(responseCode = "422")})
    public ResponseEntity save(@RequestBody @Valid TransactionRequest transactionRequest) {
        return new ResponseEntity(transactionService.save(transactionRequest), HttpStatus.CREATED);
    }
}