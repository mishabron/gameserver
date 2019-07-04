package com.mbronshteyn.gameserver.services.impl;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.Game;
import com.mbronshteyn.data.cards.Hit;
import com.mbronshteyn.data.cards.repository.CardRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.gameserver.dto.game.*;
import com.mbronshteyn.gameserver.exception.ErrorCode;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.GameService;
import com.mbronshteyn.gameserver.services.helper.PinHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PinHelper pinHelper;

    @Override
    @Transactional
    public CardDto logingCard(AuthinticateDto authDto) throws GameServerException {

        Game game = gameRepository.findByName(authDto.getGame());

        Card card = cardRepository.findByCardNumberAndGameId(authDto.getCardNumber(),game.getId());

        //validate card
        validateCard(card, authDto.getDeviceId());

        //record device id
        if(StringUtils.isEmpty(card.getDeviceId())){
            card.setDeviceId(authDto.getDeviceId());
            cardRepository.save(card);
        }

        CardDto cardDto = mapToCardDto(card, game);

        return cardDto;
    }

    @Override
    @Transactional
    public CardDto hitCard(CardHitDto cardHitDto) throws GameServerException {

        Game game = gameRepository.findByName(cardHitDto.getGame());

        Card card = cardRepository.findByCardNumberAndGameId(cardHitDto.getCardNumber(),game.getId());

        //validate card
        validateCard(card, cardHitDto.getDeviceId());

        //add new hit

        Hit hit = new Hit();
        hit.setBonusHit(cardHitDto.isBonusHit());
        hit.setNumber_1(cardHitDto.getHit1());
        hit.setNumber_2(cardHitDto.getHit2());
        hit.setNumber_3(cardHitDto.getHit3());
        hit.setNumber_4(cardHitDto.getHit4());

        card.addHit(hit);
        card.setPlayed(canPlay(card));

        cardRepository.save(card);

        CardDto cardDto = mapToCardDto(card, game);

        return cardDto;
    }

    @Override
    public String getWinningPin(CardHitDto cardHitDto) throws GameServerException {

        Game game = gameRepository.findByName(cardHitDto.getGame());

        Card card = cardRepository.findByCardNumberAndGameId(cardHitDto.getCardNumber(),game.getId());

        //validate card
        validatePlayedCard(card, cardHitDto.getDeviceId());

        return card.getWinPin();
    }

    private boolean canPlay(Card card) {

        boolean played = false;

        if(card.getHits().size() == 4){
            played = true;
        }

        return played;
    }

    private CardDto mapToCardDto(Card card, Game game){

        CardDto cardDto = new CardDto();

        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setActive(card.isActive());
        cardDto.setPlayed(card.isPlayed());
        cardDto.setPaid(card.isPaid());
        cardDto.setNumberOfHits(card.getNumberOfHits());
        cardDto.setGame(game.getName());

        switch(card.getHits().size()){
            case 0:
                cardDto.setBalance(card.getBatch().getPayout1().doubleValue());
                break;
            case 1:
                cardDto.setBalance(card.getBatch().getPayout2().doubleValue());
                break;
            case 2:
                cardDto.setBalance(card.getBatch().getPayout3().doubleValue());
                break;
        }

        card.getBatch().getPayout1();

        List<HitDto> hits = new ArrayList();
        cardDto.setHits(hits);

        for(Hit hit: card.getHits()){

            HitDto hitDto = new HitDto();
            hitDto.setSequence(hit.getSequence());
            hitDto.setBonusHit(hit.isBonusHit());

            PinNumber pinNumber1 = new PinNumber();
            pinNumber1.setNumber(hit.getNumber_1());
            pinNumber1.setGuessed(Integer.valueOf(card.getWinPin().substring(0,1)).equals(hit.getNumber_1()));
            hitDto.setNumber_1(pinNumber1);

            PinNumber pinNumber2 = new PinNumber();
            pinNumber2.setNumber(hit.getNumber_2());
            pinNumber2.setGuessed(Integer.valueOf(card.getWinPin().substring(1,2)).equals(hit.getNumber_2()));
            hitDto.setNumber_2(pinNumber2);

            PinNumber pinNumber3 = new PinNumber();
            pinNumber3.setNumber(hit.getNumber_3());
            pinNumber3.setGuessed(Integer.valueOf(card.getWinPin().substring(2,3)).equals(hit.getNumber_3()));
            hitDto.setNumber_3(pinNumber3);

            PinNumber pinNumber4 = new PinNumber();
            pinNumber4.setNumber(hit.getNumber_4());
            pinNumber4.setGuessed(Integer.valueOf(card.getWinPin().substring(3,4)).equals(hit.getNumber_4()));
            hitDto.setNumber_4(pinNumber4);

            hits.add(hitDto);
        }
        return cardDto;
    }

    private void validateCard(Card card, String deviceId) throws GameServerException {

        //validate card
        if(card  == null){
            throw new GameServerException("Card# Is Invalid",500, ErrorCode.INVALID);
        } else if(StringUtils.isNotEmpty(card.getDeviceId()) && !card.getDeviceId().equals(deviceId)){
            throw new GameServerException("Card is used by another device",500,ErrorCode.INUSE);
        }else if(!card.isActive()){
            throw new GameServerException("Card is not active",500,ErrorCode.NOTACTIVE);
        }else if(card.isPlayed()){
            throw new GameServerException("Card is played already",500,ErrorCode.PLAYED);
        }
    }

    private void validatePlayedCard(Card card, String deviceId) throws GameServerException{
        //validate card
        if(card  == null){
            throw new GameServerException("Card# Is Invalid",500, ErrorCode.INVALID);
        } else if(StringUtils.isNotEmpty(card.getDeviceId()) && !card.getDeviceId().equals(deviceId)){
            throw new GameServerException("Card is used by another device",500,ErrorCode.INUSE);
        }else if(!card.isActive()){
            throw new GameServerException("Card is not active",500,ErrorCode.NOTACTIVE);
        }else if(!card.isPlayed()){
            throw new GameServerException("Card is not played yet",500,ErrorCode.NOTPLAYED);
        }
    }
}
