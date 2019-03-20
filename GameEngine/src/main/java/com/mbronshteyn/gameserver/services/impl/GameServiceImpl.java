package com.mbronshteyn.gameserver.services.impl;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.Game;
import com.mbronshteyn.data.cards.Hit;
import com.mbronshteyn.data.cards.repository.CardRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.gameserver.dto.game.CardDto;
import com.mbronshteyn.gameserver.dto.game.HitDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.GameService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.mbronshteyn.gameserver.dto.game.AuthinticateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    GameRepository gameRepository;

    @Override
    @Transactional
    public CardDto logingCard(AuthinticateDto authDto) throws GameServerException {

        Game game = gameRepository.findByName(authDto.getGame());

        Card card = cardRepository.findByCardNumberAndGameId(authDto.getCardNumber(),game.getId());

        //validate card
        if(card  == null){
            throw new GameServerException("Card# Is Invalid",500);
        } else if(StringUtils.isNotEmpty(card.getDeviceId()) && !card.getDeviceId().equals(authDto.getDeviceId())){
            throw new GameServerException("Card is used by another device",500);
        }

        //record device id
        if(StringUtils.isEmpty(card.getDeviceId())){
            card.setDeviceId(authDto.getDeviceId());
            cardRepository.save(card);
        }

        CardDto cardDto = new CardDto();

        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setActive(card.isActive());
        cardDto.setPlayed(card.isPlayed());
        cardDto.setPaid(card.isPaid());
        cardDto.setNumberOfHits(card.getNumberOfHits());
        cardDto.setGame(game.getName());

        List<HitDto> hits = new ArrayList();
        cardDto.setHits(hits);

        for(Hit hit: card.getHits()){

            HitDto hitDto = new HitDto();
            hitDto.setSequence(hit.getSequence());
            hitDto.setBonusHit(hit.isBonusHit());
            hitDto.setNumber_1(hit.getNumber_1());
            hitDto.setNumber_2(hit.getNumber_2());
            hitDto.setNumber_3(hit.getNumber_3());
            hitDto.setNumber_4(hit.getNumber_4());

            hits.add(hitDto);
        }

        return cardDto;
    }
}
