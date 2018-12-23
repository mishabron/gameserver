package com.mbronshteyn.gameserver.services.impl;

import com.mbronshteyn.gameserver.dto.BatchDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.CardService;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Override
    public void generateCardsForBatch(BatchDto batch) throws GameServerException {

    }
}
