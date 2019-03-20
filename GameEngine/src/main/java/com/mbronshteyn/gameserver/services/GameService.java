package com.mbronshteyn.gameserver.services;

import com.mbronshteyn.gameserver.dto.game.AuthinticateDto;
import com.mbronshteyn.gameserver.dto.game.CardDto;
import com.mbronshteyn.gameserver.exception.GameServerException;

public interface GameService {

    CardDto logingCard(AuthinticateDto authDto) throws GameServerException;;
}
