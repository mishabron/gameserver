package com.mbronshteyn.gameserver.services;

import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.gameserver.dto.BatchDto;
import com.mbronshteyn.gameserver.exception.GameServerException;

public interface CardService {

    CardBatch generateCardsForBatch(BatchDto batch) throws GameServerException;;
}
