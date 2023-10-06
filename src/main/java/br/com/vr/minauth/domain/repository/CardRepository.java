package br.com.vr.minauth.domain.repository;

import br.com.vr.minauth.domain.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
