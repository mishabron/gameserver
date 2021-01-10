package com.mbronshteyn.gameserver.services;

import com.mbronshteyn.gameserver.dto.game.*;
import com.mbronshteyn.gameserver.exception.GameServerException;

public interface GameService {

    CardDto logingCard(AuthinticateDto authDto) throws GameServerException;;

    CardDto hitCard(CardHitDto cardHitDto) throws GameServerException;

    String getWinningPin(CardHitDto cardHitDto) throws GameServerException;

    void saveEmail(WinnerEmailDto winnerEmailDto) throws GameServerException;

    HistoryDto getHistory(AuthinticateDto authDto) throws GameServerException;
}
