package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.CardBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBatchRepository extends JpaRepository<CardBatch, Integer> {

    public CardBatch findByBarcode(String barcode);

}
