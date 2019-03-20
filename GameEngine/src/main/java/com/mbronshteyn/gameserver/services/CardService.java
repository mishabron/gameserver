package com.mbronshteyn.gameserver.services;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.gameserver.dto.card.BatchDto;
import com.mbronshteyn.gameserver.exception.GameServerException;

public interface CardService {

    CardBatch generateCardsForBatch(BatchDto batch) throws GameServerException;

    Card activateCard(String barcode) throws GameServerException;

    CardBatch activateBatch(String barcode) throws GameServerException;
}
