package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.BonusPin;
import com.mbronshteyn.data.cards.keys.AuxiliaryPinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BonusPinRepository extends JpaRepository<BonusPin, AuxiliaryPinId> {

    public BonusPin findByCardNumber(Long cardNumber);

    @Transactional
    @Modifying
    @Query("delete from BonusPin s where s.id.batch.id=:batchId")
    void deleteByBatchId(@Param("batchId") int batchId);

}
