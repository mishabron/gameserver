package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CardRepository extends JpaRepository<Card, BigInteger> {


}
