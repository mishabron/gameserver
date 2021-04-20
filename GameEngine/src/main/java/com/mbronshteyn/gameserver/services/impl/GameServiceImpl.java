package com.mbronshteyn.gameserver.services.impl;

import com.mbronshteyn.data.cards.*;
import com.mbronshteyn.data.cards.keys.AuxiliaryPinId;
import com.mbronshteyn.data.cards.repository.*;
import com.mbronshteyn.gameserver.dto.game.*;
import com.mbronshteyn.gameserver.exception.ErrorCode;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.mail.Mail;
import com.mbronshteyn.gameserver.services.GameService;
import com.mbronshteyn.gameserver.services.helper.PinHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PinHelper pinHelper;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    HitPinRepository hitPinRepository;

    @Autowired
    BonusPinRepository bonusPinRepository;

    @Autowired
    SuperPinRepository superPinRepository;

    @Override
    @Transactional
    public CardDto logingCard(AuthinticateDto authDto) throws GameServerException {

        Game game = gameRepository.findByName(authDto.getGame());
        validateGame(game);

        Card card = cardRepository.findByCardNumberAndGameId(authDto.getCardNumber(),game.getId());

        //validate card
        validateCard(card, authDto.getDeviceId());

        //record device id
        if(StringUtils.isEmpty(card.getDeviceId())){
            card.setDeviceId(authDto.getDeviceId());
            cardRepository.save(card);
        }

        CardDto cardDto = mapToCardDto(card, game);

        //check for bonuses
        if (card.getCurrentPlay()==0 && card.getNumberOfHits() > 0) {
            int lastSeq = card.getLastPlay().getLastNonBunusHitSequence();
            Hit hit = card.getLastPlay().getLastNonBunusHit();
            if (hit != null) {
                BonusPin bonusPin = bonusPinRepository.findOne(new AuxiliaryPinId(card.getBatch(), lastSeq, hit.getRequestNo()));
                if (bonusPin != null && bonusPin.getCardNumber().equals(card.getCardNumber()) && !bonusPin.isUsed()) {
                    cardDto.setBonusPin(Bonus.BONUSPIN);
                }
                SuperPin superPin = superPinRepository.findOne(new AuxiliaryPinId(card.getBatch(), lastSeq, hit.getRequestNo()));
                if (superPin != null && superPin.getCardNumber().equals(card.getCardNumber()) && !superPin.isUsed()) {
                    cardDto.setBonusPin(Bonus.SUPERPIN);
                }
            }
        }

        return cardDto;
    }

    @Override
    @Transactional
    public CardDto hitCard(CardHitDto cardHitDto) throws GameServerException {

        Game game = gameRepository.findByName(cardHitDto.getGame());
        validateGame(game);

        Card card = cardRepository.findByCardNumberAndGameId(cardHitDto.getCardNumber(),game.getId());

        //validate card
        validateCard(card, cardHitDto.getDeviceId());

        //add new hit

        Hit hit = new Hit();
        hit.setBonusHit(cardHitDto.getBonus());
        hit.setNumber_1(cardHitDto.getHit1());
        hit.setNumber_2(cardHitDto.getHit2());
        hit.setNumber_3(cardHitDto.getHit3());
        hit.setNumber_4(cardHitDto.getHit4());
        hit.setBatch(card.getBatch());
        hit.setFirstPlay(card.getCurrentPlay()==0);

        card.addHit(hit);
        card.setPlayed(canPlay(card));

        CardDto cardDto = mapToCardDto(card, game);

        int lastSeq = card.getLastPlay().getLastNonBunusHitSequence();
        int hitRequestNo = hitPinRepository.countByFirstPlayAndSequenceAndBatch(true, lastSeq, card.getBatch());
        hit.setRequestNo(hitRequestNo);
        //bonus hit only for first game
        if (card.getCurrentPlay()==0) {
            BonusPin bonusPin = bonusPinRepository.findOne(new AuxiliaryPinId(card.getBatch(), lastSeq, hitRequestNo));
            if (bonusPin != null && bonusPin.isActive() && !bonusPin.isUsed()) {
                cardDto.setBonusPin(Bonus.BONUSPIN);
                bonusPin.setCardNumber(card.getCardNumber());
                bonusPin.setActive(false);
                bonusPin.setUsed(true);
                bonusPinRepository.save(bonusPin);
            }
            SuperPin superPin = superPinRepository.findOne(new AuxiliaryPinId(card.getBatch(), lastSeq, hitRequestNo));
            if (superPin != null && superPin.isActive() && !superPin.isUsed()) {
                cardDto.setBonusPin(Bonus.SUPERPIN);
                superPin.setCardNumber(card.getCardNumber());
                superPin.setActive(false);
                superPin.setUsed(true);
                superPinRepository.save(superPin);
            }
        }

        //check and create free game
        if(isWinnig(card) && card.getCurrentPlay() == 0  && card.getLastPlay().getNonBonusHits().size() == 4){
            card = createFreeGame(card);
        }

        cardRepository.save(card);

        return cardDto;
    }

    public Card createFreeGame(Card card) {

        Play play = new Play();
        play.setPlayNumber(card.getCurrentPlay()+1);
        play.setCard(card);
        card.getPlays().add(play);
        card.setCurrentPlay(card.getCurrentPlay()+1);
        card.setPlayed(false);
        card.setWinPin(pinHelper.generateUniquePin(card));
        card.setNumberOfHits(0);

        return card;
    }

    @Override
    @Transactional
    public String getWinningPin(CardHitDto cardHitDto) throws GameServerException {

        Game game = gameRepository.findByName(cardHitDto.getGame());
        validateGame(game);

        Card card = cardRepository.findByCardNumberAndGameId(cardHitDto.getCardNumber(),game.getId());

        //validate card
        validatePlayedCard(card, cardHitDto.getDeviceId());

        return card.getWinPin();
    }

    @Override
    @Transactional
    public void saveEmail(WinnerEmailDto winnerEmailDto) throws GameServerException {

        Game game = gameRepository.findByName(winnerEmailDto.getGame());
        validateGame(game);

        Card card = cardRepository.findByCardNumberAndGameId(winnerEmailDto.getCardNumber(),game.getId());

        //validate card
        validateWinningCard(card, winnerEmailDto.getDeviceId());

        card.setEmail(winnerEmailDto.getEmail());
        cardRepository.save(card);

        sendEmailToPlayer(card);
    }

    @Override
    public HistoryDto getHistory(AuthinticateDto authDto) throws GameServerException {

        HistoryDto historyDto = new HistoryDto();

        Game game = gameRepository.findByName(authDto.getGame());
        validateGame(game);

        Card card = cardRepository.findByCardNumberAndGameId(authDto.getCardNumber(),game.getId());
        //validate card
        validateCard(card, authDto.getDeviceId());

        historyDto.setCardNumber(card.getCardNumber());

        for(Play play : card.getPlays()){
            PlayDto playDto = new PlayDto();
            playDto.setHits(getHitsDto(play));
            playDto.setPlayNumber(play.getPlayNumber());
            historyDto.addPlay(playDto);
        }
        return historyDto;
    }

    private void sendEmailToPlayer(Card card) throws GameServerException {

        if(isWinnig(card)){

            Mail mail = new Mail();
            mail.setFrom("winner@pingo.win");
            mail.setTo(card.getEmail());
            mail.setSubject("Sending Simple Email with JavaMailSender Example");
            mail.setContent("This tutorial demonstrates how to send a simple email winner");

            emailService.sendEmail(mail);
        }

    }

    private boolean canPlay(Card card) {

        boolean played = false;

        if((card.getLastPlay().getNonBonusHits().size() == 4 && card.getCurrentPlay() ==0)  || (card.getLastPlay().getNonBonusHits().size() == 3 && card.getCurrentPlay() ==1) || isWinnig(card)){
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
        cardDto.setFreeGame(card.getCurrentPlay()>0);
        cardDto.setEmail(card.getEmail());

        int attempts = card.getLastPlay().getNonBonusHits().size();
        if(isWinnig(card)){
            //attempts--;
        }
        else{
            if(card.getLastPlay().getBonusHits().isPresent()){
                attempts--;
            }
        }
        switch(attempts){
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

        cardDto.setPayout1(card.getBatch().getPayout1().doubleValue());
        cardDto.setPayout2(card.getBatch().getPayout2().doubleValue());
        cardDto.setPayout3(card.getBatch().getPayout3().doubleValue());

        List<HitDto> hits = new ArrayList();
        cardDto.setHits(getHitsDto(card.getLastPlay()));

        for(Hit hit: card.getLastPlay().getHits()){

            HitDto hitDto = new HitDto();
            hitDto.setSequence(hit.getSequence());
            hitDto.setBonusHit(hit.getBonusHit());

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

    public boolean isWinnig(Card card) {

        boolean winning = false;

        List<Hit> hits = card.getLastPlay().getHits();

        List<Integer> win1 = hits.stream().map(h -> h.getNumber_1()).filter(n -> n == Integer.valueOf(card.getWinPin().substring(0, 1))).collect(Collectors.toList());
        List<Integer> win2 = hits.stream().map(h -> h.getNumber_2()).filter(n -> n == Integer.valueOf(card.getWinPin().substring(1, 2))).collect(Collectors.toList());
        List<Integer> win3 = hits.stream().map(h -> h.getNumber_3()).filter(n -> n == Integer.valueOf(card.getWinPin().substring(2, 3))).collect(Collectors.toList());
        List<Integer> win4 = hits.stream().map(h -> h.getNumber_4()).filter(n -> n == Integer.valueOf(card.getWinPin().substring(3, 4))).collect(Collectors.toList());

        if(!win1.isEmpty() && !win2.isEmpty() && !win3.isEmpty() && !win4.isEmpty()){
            winning = true;
        }

        return winning;
    }

    private List<HitDto> getHitsDto(Play card){

        List<HitDto> hits = new ArrayList();

        for(Hit hit: card.getHits()){

            HitDto hitDto = new HitDto();
            hitDto.setSequence(hit.getSequence());
            hitDto.setBonusHit(hit.getBonusHit());

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

        return hits;
    }

    private void validateCard(Card card, String deviceId) throws GameServerException {

        //validate card
        if(card  == null){
            throw new GameServerException("Card# Is Invalid",500, ErrorCode.INVALID);
        } else if(StringUtils.isNotEmpty(card.getDeviceId()) && !card.getDeviceId().equals(deviceId)){
            throw new GameServerException("Card is used by another device",500,ErrorCode.INUSE);
        }else if(!card.isActive()){
            throw new GameServerException("Card is not active",500,ErrorCode.NOTACTIVE);
        }else if(card.isPlayed() && !isWinnig(card)){
            throw new GameServerException("Card is played already",500,ErrorCode.PLAYED);
        }
    }

    private void validatePlayedCard(Card card, String deviceId) throws GameServerException{

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

    private void validateWinningCard(Card card, String deviceId) throws GameServerException{
        //validate card
        if(card  == null){
            throw new GameServerException("Card# Is Invalid",500, ErrorCode.INVALID);
        } else if(StringUtils.isNotEmpty(card.getDeviceId()) && !card.getDeviceId().equals(deviceId)){
            throw new GameServerException("Card is used by another device",500,ErrorCode.INUSE);
        }else if(!card.isActive()){
            throw new GameServerException("Card is not active",500,ErrorCode.NOTACTIVE);
        }else if(!card.isPlayed()){
            throw new GameServerException("Card is not played yet",500,ErrorCode.NOTPLAYED);
        }else if(!isWinnig(card)){
            throw new GameServerException("Is not winning cardr",500,ErrorCode.NOTWINNER);
        }
    }

    private void validateGame(Game game) throws GameServerException{
        if(game  == null){
            throw new GameServerException("Game# Is Invalid",500, ErrorCode.INVALID);
        }
    }
}
