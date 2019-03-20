package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CardRepository extends JpaRepository<Card, BigInteger> {

    public Card findByBarcode(String barcode);

    public Card findByCardNumber(Long cardNumber);

    public Card findByCardNumberAndGameId(Long cardNumber, int gameId);

}
