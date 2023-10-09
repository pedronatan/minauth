package br.com.vr.minauth.api.controller;

import br.com.vr.minauth.api.request.CardRequest;
import br.com.vr.minauth.api.response.CardResponse;
import br.com.vr.minauth.domain.service.CardService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "201"), @ApiResponse(responseCode = "422")})
    public ResponseEntity<CardResponse> save(@RequestBody @Valid CardRequest cardRequest) {
        return new ResponseEntity<>(cardService.save(cardRequest), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{numeroCartao}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404")})
    public ResponseEntity<BigDecimal> getBalanceByNumber(@PathVariable String numeroCartao){
        return ResponseEntity.ok(cardService.findBalanceByNumber(numeroCartao));
    }
}