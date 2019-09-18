package com.mbronshteyn.gameserver.services;

import com.mbronshteyn.gameserver.dto.game.AuthinticateDto;
import com.mbronshteyn.gameserver.dto.game.CardDto;
import com.mbronshteyn.gameserver.dto.game.CardHitDto;
import com.mbronshteyn.gameserver.dto.game.WinnerEmailDto;
import com.mbronshteyn.gameserver.exception.GameServerException;

public interface GameService {

    CardDto logingCard(AuthinticateDto authDto) throws GameServerException;;

    CardDto hitCard(CardHitDto cardHitDto) throws GameServerException;

    String getWinningPin(CardHitDto cardHitDto) throws GameServerException;

    void saveEmail(WinnerEmailDto winnerEmailDto) throws GameServerException;
}
