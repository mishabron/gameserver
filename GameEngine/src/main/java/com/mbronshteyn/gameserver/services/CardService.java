package com.mbronshteyn.gameserver.services;

import com.mbronshteyn.gameserver.dto.BatchDto;
import com.mbronshteyn.gameserver.exception.GameServerException;

public interface CardService {

    void generateCardsForBatch(BatchDto batch) throws GameServerException;;
}
