package br.com.vr.minauth.domain.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public interface CardBalanceView {

    BigDecimal getBalance();
}
