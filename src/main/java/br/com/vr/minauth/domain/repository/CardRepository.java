package br.com.vr.minauth.domain.repository;

import br.com.vr.minauth.domain.entity.CardBalanceView;
import br.com.vr.minauth.domain.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    Optional<CardEntity> findByNumber(String number);

    Optional<CardBalanceView> findBalanceByNumber(String number);

    Optional<CardEntity> findByNumberAndPassword(String number, String password);

    Optional<CardEntity> findByNumberAndPasswordAndBalanceGreaterThanEqual(String number, String password, BigDecimal balance);
}