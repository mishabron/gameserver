package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.BonusPin;
import com.mbronshteyn.data.cards.keys.AuxiliaryPinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusPinRepository extends JpaRepository<BonusPin, AuxiliaryPinId> {


}
