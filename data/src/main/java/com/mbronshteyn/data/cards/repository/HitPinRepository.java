package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.data.cards.Hit;
import com.mbronshteyn.data.cards.Play;
import com.mbronshteyn.data.cards.keys.HitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HitPinRepository extends JpaRepository<Hit, HitId> {

    public int countByFirstPlayAndSequenceAndBatch(boolean play, int sequence, CardBatch batch);

}
